package com.passchecker.passcheckerbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/doc")
public class SwaggerController {

    @GetMapping
    public String redirectToSwagger(HttpServletRequest request) {
        Integer portNumber = Integer.parseInt(request.getHeader("Host").split(":")[1]);
        return "redirect:http://localhost:" + portNumber + "/swagger-ui/index.html";
    }
}
