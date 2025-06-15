package com.passchecker.passcheckerbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for redirecting requests to the Swagger UI documentation page.
 * Dynamically constructs the Swagger URL based on the current server port.
 */
@Controller
@RequestMapping("/api/doc")
public class SwaggerController {

    /**
     * Redirects the request to the Swagger UI page.
     * Extracts the current server port from the request headers and constructs the Swagger URL accordingly.
     *
     * @param request the HTTP request containing the host and port information
     * @return a redirect string pointing to the Swagger UI page
     */
    @GetMapping
    public String redirectToSwagger(HttpServletRequest request) {
        Integer portNumber = Integer.parseInt(request.getHeader("Host").split(":")[1]);
        return "redirect:http://localhost:" + portNumber + "/swagger-ui/index.html";
    }
}
