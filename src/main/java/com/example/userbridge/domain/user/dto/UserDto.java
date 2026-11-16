package com.example.userbridge.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Builder
public record UserDto(
        Long id,

        @NotBlank(message = "Imię jest wymagane")
        String firstName,

        @NotBlank(message = "Nazwisko jest wymagane")
        String lastName,
        @Email(message = "Podaj poprawny adres e-mail")
        @NotBlank(message = "Adres e-mail jest wymagany")
        String email,


        @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Nieprawidłowy numer telefonu")
        String phoneNumber,

        String street,

        @Pattern(regexp = "\\d{2}-\\d{3}", message = "Nieprawidłowy kod pocztowy (format 00-000)")
        String postalCode,

        String city
) {}