package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class TokenNotFoundExceptionTest {

    @Test
     void testExceptionMessage() {
        String message = "Link aktywacyjny jest nieprawidłowy lub został już użyty";
        TokenNotFoundException exception = assertThrows(
                TokenNotFoundException.class,
                () -> { throw new TokenNotFoundException(message); }
        );

        assertEquals(message, exception.getMessage());
    }

    @Test
     void testExceptionInheritance() {
        TokenNotFoundException exception = new TokenNotFoundException("Link aktywacyjny jest nieprawidłowy lub został już użyty");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
