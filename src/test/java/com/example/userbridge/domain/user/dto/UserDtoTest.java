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

 class UserDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void whenFirstNameIsBlank_thenValidationFails() {
        UserDto userDto = UserDto.builder()
                .firstName("")
                .lastName("Kowalski")
                .email("test@example.com")
                .phoneNumber("123456789")
                .street("Street")
                .postalCode("12-345")
                .city("City")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
        assertEquals("First name is mandatory", violations.iterator().next().getMessage());
    }

    @Test
     void whenLastNameIsBlank_thenValidationFails() {
        UserDto userDto = UserDto.builder()
                .firstName("Jan")
                .lastName("")
                .email("test@example.com")
                .phoneNumber("123456789")
                .street("Street")
                .postalCode("12-345")
                .city("City")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
        assertEquals("Last name is mandatory", violations.iterator().next().getMessage());
    }

    @Test
     void whenEmailIsInvalid_thenValidationFails() {
        UserDto userDto = UserDto.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("invalid-email")
                .phoneNumber("123456789")
                .street("Street")
                .postalCode("12-345")
                .city("City")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
    }

    @Test
     void whenPhoneNumberIsInvalid_thenValidationFails() {
        UserDto userDto = UserDto.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@example.com")
                .phoneNumber("invalid-phone")
                .street("Street")
                .postalCode("12-345")
                .city("City")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
        assertEquals("Phone number is invalid", violations.iterator().next().getMessage());
    }

    @Test
     void whenPostalCodeIsInvalid_thenValidationFails() {
        UserDto userDto = UserDto.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@example.com")
                .phoneNumber("123456789")
                .street("Street")
                .postalCode("invalid")
                .city("City")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
        assertEquals("Postal code is invalid", violations.iterator().next().getMessage());
    }

    @Test
     void whenAllFieldsAreValid_thenValidationSucceeds() {
        UserDto userDto = UserDto.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@example.com")
                .phoneNumber("123456789")
                .street("Street")
                .postalCode("12-345")
                .city("City")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertTrue(violations.isEmpty());
    }
}