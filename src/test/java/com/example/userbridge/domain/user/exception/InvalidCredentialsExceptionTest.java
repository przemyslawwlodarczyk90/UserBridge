package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidCredentialsExceptionTest {

    @Test
    void testInvalidCredentialsExceptionMessage() {
        String errorMessage = "Invalid email or password";

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            throw new InvalidCredentialsException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}