package com.example.userbridge.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDto(
        @NotBlank(message = "Brak tokenu")
        String token,

        @NotBlank(message = "Hasło jest wymagane")
        @Size(min = 8, max = 100, message = "Hasło musi mieć od 8 do 100 znaków")
        String newPassword
) {}
