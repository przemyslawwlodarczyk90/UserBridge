package com.example.userbridge.domain.user.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenEmailIsInvalid_thenValidationFails() {
        LoginDto loginDto = new LoginDto("invalid-email", "password123");

        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);

        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
    }



    @Test
    void whenPasswordIsBlank_thenValidationFails() {
        LoginDto loginDto = new LoginDto("test@example.com", "");

        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);

        assertEquals(1, violations.size());
        assertEquals("Password is mandatory", violations.iterator().next().getMessage());
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        LoginDto loginDto = new LoginDto("test@example.com", "password123");

        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);

        assertTrue(violations.isEmpty());
    }
}
