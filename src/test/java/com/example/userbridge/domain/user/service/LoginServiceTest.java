package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.InvalidCredentialsException;
import com.example.userbridge.infrastructure.repository.UserRepository;
import com.example.userbridge.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

 class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testLoginSuccess() {
        LoginDto loginDto = new LoginDto("jan.kowalski@example.com", "password123");
        User user = new User();
        user.setEmail(loginDto.email());
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(loginDto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDto.password(), user.getPassword())).thenReturn(true);
        when(jwtService.createToken(user.getEmail())).thenReturn("JWT_TOKEN");

        String token = loginService.login(loginDto);

        verify(userRepository).findByEmail(loginDto.email());
        verify(passwordEncoder).matches(loginDto.password(), user.getPassword());
        verify(jwtService).createToken(user.getEmail());
        assertEquals("JWT_TOKEN", token);
    }

    @Test
     void testLoginInvalidEmail() {
        LoginDto loginDto = new LoginDto("invalid.email@example.com", "password123");

        when(userRepository.findByEmail(loginDto.email())).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> loginService.login(loginDto));

        verify(userRepository).findByEmail(loginDto.email());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).createToken(anyString());
    }

    @Test
     void testLoginInvalidPassword() {
        LoginDto loginDto = new LoginDto("jan.kowalski@example.com", "invalidPassword");
        User user = new User();
        user.setEmail(loginDto.email());
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(loginDto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDto.password(), user.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> loginService.login(loginDto));

        verify(userRepository).findByEmail(loginDto.email());
        verify(passwordEncoder).matches(loginDto.password(), user.getPassword());
        verify(jwtService, never()).createToken(anyString());
    }
}
