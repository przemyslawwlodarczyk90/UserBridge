package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailAlreadyInUseExceptionTest {

    @Test
    void testExceptionMessage() {

        String expectedMessage = "Email is already in use by an active user";


        EmailAlreadyInUseException exception = assertThrows(EmailAlreadyInUseException.class, () -> {
            throw new EmailAlreadyInUseException(expectedMessage);
        });


        assertEquals(expectedMessage, exception.getMessage());
    }
}
