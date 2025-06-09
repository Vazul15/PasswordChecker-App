package com.passchecker.passcheckerbackend;

import com.passchecker.passcheckerbackend.exception.ResourceNotFoundException;
import com.passchecker.passcheckerbackend.model.dto.FindPasswordPathsDTO;
import com.passchecker.passcheckerbackend.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PasswordTests {

    @InjectMocks
    private PasswordService passwordService;

    @Test
    void testFindPasswordValidPathAndPassword() throws IOException, ResourceNotFoundException {

        Path tempDir = Files.createTempDirectory("test-files");
        Path tempFile = Files.createFile(tempDir.resolve("testFile.txt"));

        String password = "secret123";
        Files.write(tempFile, ("This is a test file containing the password: " + password).getBytes(StandardCharsets.UTF_8));

        String path = tempDir.toAbsolutePath().toString();
        System.out.println("Checking path in container: " + path);

        FindPasswordPathsDTO result = passwordService.findPasswordByPathAndPassword(path, password);
        assertFalse(result.paths().isEmpty());

        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testFindPasswordValidPathNotContainingPassword() throws IOException {

        Path tempDir = Files.createTempDirectory("test-files");
        Path tempFile = Files.createFile(tempDir.resolve("testFile.txt"));
        String passwordToCheck = "das";

        Files.write(tempFile, ("This is a test file containing the password").getBytes(StandardCharsets.UTF_8));

        String path = tempDir.toAbsolutePath().toString();
        System.out.println("Checking path in container: " + path);

        FindPasswordPathsDTO result = passwordService.findPasswordByPathAndPassword(path, passwordToCheck);
        assertTrue(result.paths().get(0).equals("No files found with the matching password"));

        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testFindPasswordNullPath() {
        String password = "secret123";
        assertThrows(IllegalArgumentException.class, () -> passwordService.findPasswordByPathAndPassword(null, password));
    }

    @Test
    void testFindPasswordNullFiles() throws IOException {
        Path tempDir = Files.createTempDirectory("test-files");
        String password = "secret123";

        String path = tempDir.toAbsolutePath().toString();

        assertThrows(ResourceNotFoundException.class, () -> passwordService.findPasswordByPathAndPassword(path, password));
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testFindPasswordEmptyPath() {
        String path = "";
        String password = "secret123";

        assertThrows(IllegalArgumentException.class, () -> passwordService.findPasswordByPathAndPassword(path, password));
    }

}
