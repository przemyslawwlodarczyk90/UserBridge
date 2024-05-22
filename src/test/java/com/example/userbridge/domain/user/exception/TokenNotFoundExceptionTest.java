package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class TokenNotFoundExceptionTest {

    @Test
     void testExceptionMessage() {
        String message = "Token not found";
        TokenNotFoundException exception = assertThrows(
                TokenNotFoundException.class,
                () -> { throw new TokenNotFoundException(message); }
        );

        assertEquals(message, exception.getMessage());
    }

    @Test
     void testExceptionInheritance() {
        TokenNotFoundException exception = new TokenNotFoundException("Token not found");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
