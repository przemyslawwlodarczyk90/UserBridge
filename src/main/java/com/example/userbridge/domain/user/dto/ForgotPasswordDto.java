package com.example.userbridge.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordDto(
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is mandatory")
        String email
) {}
