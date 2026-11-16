package com.example.userbridge.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record LoginDto(
        @Email(message = "Podaj poprawny adres e-mail")
        @NotBlank(message = "Adres e-mail jest wymagany")
        String email,

        @NotBlank(message = "Hasło jest wymagane")
        String password
) {}
