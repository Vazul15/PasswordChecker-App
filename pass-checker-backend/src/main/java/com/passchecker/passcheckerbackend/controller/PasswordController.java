package com.passchecker.passcheckerbackend.controller;

import com.passchecker.passcheckerbackend.model.dto.FindPasswordPathsDTO;
import com.passchecker.passcheckerbackend.service.PasswordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/findpass")
public class PasswordController {

    PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/")
    public FindPasswordPathsDTO getAllDirectories(@RequestParam String path, @RequestParam String password) throws IOException {
    return passwordService.findPasswordByPathAndPassword(path, password);
    }
}
