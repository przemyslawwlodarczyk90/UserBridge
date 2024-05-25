package com.example.userbridge.domain.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNotActivatedExceptionTest {

    @Test
    void testExceptionMessage() {

        String expectedMessage = "User is not activated";


        UserNotActivatedException exception = assertThrows(UserNotActivatedException.class, () -> {
            throw new UserNotActivatedException(expectedMessage);
        });


        assertEquals(expectedMessage, exception.getMessage());
    }
}
