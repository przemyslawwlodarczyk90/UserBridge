package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.InvalidCredentialsException;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jan.kowalski@example.com");
        loginDto.setPassword("password123");

        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .street("ul. Marszałkowska 123")
                .postalCode("00-001")
                .city("Warszawa")
                .password("password123") // Dodane pole
                .build();

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));

        String token = loginService.login(loginDto);

        assertEquals("JWT_TOKEN", token);
    }

    @Test
    void testLoginInvalidPassword() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jan.kowalski@example.com");
        loginDto.setPassword("wrongpassword");

        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .street("ul. Marszałkowska 123")
                .postalCode("00-001")
                .city("Warszawa")
                .password("password123") // Dodane pole
                .build();

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(InvalidCredentialsException.class, () -> loginService.login(loginDto));
    }

    @Test
    void testLoginUserNotFound() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jan.kowalski@example.com");
        loginDto.setPassword("password123");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> loginService.login(loginDto));
    }
}