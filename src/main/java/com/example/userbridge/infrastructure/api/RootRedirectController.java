package com.example.userbridge.infrastructure.api;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/** The backend serves only the API - opening it in a browser redirects to the React frontend. */
@Hidden
@RestController
public class RootRedirectController {

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @GetMapping("/")
    public ResponseEntity<Void> redirectToFrontend() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, URI.create(frontendUrl).toString())
                .build();
    }
}
