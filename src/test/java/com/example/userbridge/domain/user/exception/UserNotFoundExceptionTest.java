package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNotFoundExceptionTest {

    @Test
    void testUserNotFoundExceptionMessage() {
        String errorMessage = "User not found";

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}