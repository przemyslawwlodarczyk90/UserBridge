package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class TokenExpiredExceptionTest {

    @Test
     void testExceptionMessage() {
        String message = "Token has expired";
        TokenExpiredException exception = assertThrows(
                TokenExpiredException.class,
                () -> { throw new TokenExpiredException(message); }
        );

        assertEquals(message, exception.getMessage());
    }

    @Test
     void testExceptionInheritance() {
        TokenExpiredException exception = new TokenExpiredException("Token has expired");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
