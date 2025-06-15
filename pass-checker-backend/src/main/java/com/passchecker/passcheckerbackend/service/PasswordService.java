package com.passchecker.passcheckerbackend.service;

import com.passchecker.passcheckerbackend.exception.ResourceNotFoundException;
import com.passchecker.passcheckerbackend.model.dto.FindPasswordPathsDTO;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class responsible for searching for passwords within files and directories.
 * Supports recursive file scanning and content matching.
 */
@Service
public class PasswordService {

    /**
     * Searches for a given password within files located in the specified directory path.
     *
     * @param path     the root directory where the search will be conducted
     * @param password the password to search for within the files
     * @return a {@link FindPasswordPathsDTO} object containing paths where the password was found
     * @throws IOException if an error occurs while reading files
     * @throws ResourceNotFoundException if the specified directory is empty or does not exist
     * @throws IllegalArgumentException if the provided path is null or empty
     */
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

    /**
     * Recursively scans all files and directories for the specified password.
     *
     * @param files    an array of {@link File} objects representing files and directories
     * @param password the password to search for
     * @return a {@link FindPasswordPathsDTO} object containing paths where the password was found
     * @throws IOException if an error occurs while reading files
     */
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

    /**
     * Reads the content of a file and processes its text.
     * Removes unnecessary characters and trims the content for accurate searching.
     *
     * @param filePath the absolute path of the file to be read
     * @return a {@link String} representing the cleaned content of the file
     * @throws IOException if an error occurs while reading the file
     */
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

    /**
     * Checks if the given data contains the specified password.
     *
     * @param data     the text content extracted from a file
     * @param password the password to search for
     * @return {@code true} if the password is found, otherwise {@code false}
     */
    private boolean itContainsPassword(String data, String password) {
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(data.split(" ")));
        return parts.stream()
                .anyMatch(part -> part.equals(password));
    }
}
