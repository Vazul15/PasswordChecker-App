package com.passchecker.passcheckerbackend.controller;

import com.passchecker.passcheckerbackend.model.dto.FindPasswordPathsDTO;
import com.passchecker.passcheckerbackend.service.PasswordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST controller responsible for handling password lookup operations.
 * Provides an endpoint for retrieving directories where passwords are found.
 */
@RestController
@RequestMapping("/api/findpass")
public class PasswordController {

    private final PasswordService passwordService;

    /**
     * Constructs the controller with the provided {@link PasswordService}.
     *
     * @param passwordService the service handling password search logic
     */
    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /**
     * Retrieves all directories where the specified password appears.
     *
     * @param path     the root directory to search
     * @param password the password to look for within the directory structure
     * @return a {@link FindPasswordPathsDTO} containing the paths where the password was found
     * @throws IOException if an error occurs while accessing files
     */
    @GetMapping("/")
    public FindPasswordPathsDTO getAllDirectories(@RequestParam String path, @RequestParam String password) throws IOException {
        return passwordService.findPasswordByPathAndPassword(path, password);
    }
}
