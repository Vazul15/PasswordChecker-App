package com.passchecker.passcheckerbackend.service;

import com.passchecker.passcheckerbackend.exception.ResourceNotFoundException;
import com.passchecker.passcheckerbackend.model.dto.FindPasswordPathsDTO;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PasswordService {

    public FindPasswordPathsDTO findPasswordByPathAndPassword(String path, String password) throws IOException, ResourceNotFoundException, IllegalArgumentException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }
        File[] files = new File(path).listFiles();

        if (files == null || files.length == 0) {
            throw new ResourceNotFoundException("File not found");
        }

        FindPasswordPathsDTO checkedFiles = getAllFiles(files, password);
        System.out.println(checkedFiles);
        return checkedFiles.paths().size() > 0 ? checkedFiles : new FindPasswordPathsDTO(List.of("No files found with the matching password"));
    }

    private FindPasswordPathsDTO getAllFiles(File[] files, String password) throws IOException {
        List<String> passwordFoundOnFiles = new ArrayList<>();
        for (File fileName : files) {
            if (fileName.isDirectory()) {
                FindPasswordPathsDTO nestedResult = getAllFiles(fileName.listFiles(), password);
                passwordFoundOnFiles.addAll(nestedResult.paths());
            } else {
                String fileString = readFile(fileName.getAbsolutePath());
                if (itContainsPassword(fileString, password)) {
                    passwordFoundOnFiles.add(fileName.getAbsolutePath());
                }
            }
        }
        return new FindPasswordPathsDTO(passwordFoundOnFiles);
    }

    private String readFile(String filePath) throws IOException {
        StringBuilder fullText = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.endsWith("\\")) {
                    line = line.substring(0, line.length() - 1).replace("\"", "");
                    fullText.append(line.trim());
                } else {
                    fullText.append(line.replace("\"", "").trim()).append(" ");
                }
            }
        }
        System.out.println(fullText);
        return fullText.toString();
    }

    private boolean itContainsPassword(String data, String password) {
        ArrayList<String> parts = new ArrayList<>(
                Arrays.asList(data.split(" ")));
        return parts.stream()
                .anyMatch(part -> part.equals(password));
    }
}
