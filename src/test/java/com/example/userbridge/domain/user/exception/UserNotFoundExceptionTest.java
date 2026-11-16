package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class UserNotFoundExceptionTest {

    @Test
     void testExceptionMessage() {
        String message = "Nie znaleziono użytkownika";
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> { throw new UserNotFoundException(message); }
        );

        assertEquals(message, exception.getMessage());
    }

    @Test
     void testExceptionInheritance() {
        UserNotFoundException exception = new UserNotFoundException("Nie znaleziono użytkownika");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}