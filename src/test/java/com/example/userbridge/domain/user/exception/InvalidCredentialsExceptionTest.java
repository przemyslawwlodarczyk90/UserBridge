package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class InvalidCredentialsExceptionTest {

    @Test
     void testExceptionMessage() {
        String message = "Invalid email or password";
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> { throw new InvalidCredentialsException(message); }
        );

        assertEquals(message, exception.getMessage());
    }

    @Test
     void testExceptionInheritance() {
        InvalidCredentialsException exception = new InvalidCredentialsException("Invalid email or password");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
