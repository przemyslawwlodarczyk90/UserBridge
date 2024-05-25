package com.example.userbridge.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginDto(
        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotEmpty(message = "Password is required")
        String password
) {}
