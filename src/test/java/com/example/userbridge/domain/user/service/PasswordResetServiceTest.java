package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.entity.PasswordResetToken;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.TokenExpiredException;
import com.example.userbridge.domain.user.exception.TokenNotFoundException;
import com.example.userbridge.infrastructure.repository.PasswordResetTokenRepository;
import com.example.userbridge.infrastructure.repository.UserRepository;
import com.example.userbridge.infrastructure.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PasswordResetServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordResetTokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MailService mailService;

    @InjectMocks
    private PasswordResetService passwordResetService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(passwordResetService, "frontendUrl", "http://localhost:5173");
        ReflectionTestUtils.setField(passwordResetService, "expirationMinutes", 30L);
        user = User.builder().id(1L).firstName("Jan").email("jan@example.com").password("old").enabled(true).build();
    }

    @Test
    void requestReset_existingUser_savesTokenAndSendsEmail() {
        when(userRepository.findByEmail("jan@example.com")).thenReturn(Optional.of(user));

        passwordResetService.requestReset("jan@example.com");

        ArgumentCaptor<PasswordResetToken> tokenCaptor = ArgumentCaptor.forClass(PasswordResetToken.class);
        verify(tokenRepository).deleteByUser(user);
        verify(tokenRepository).save(tokenCaptor.capture());
        PasswordResetToken saved = tokenCaptor.getValue();
        assertEquals(user, saved.getUser());
        assertTrue(saved.getExpiresAt().isAfter(LocalDateTime.now().plusMinutes(29)));

        verify(mailService).sendPasswordResetEmail(eq("jan@example.com"), eq("Jan"),
                eq("http://localhost:5173/reset-password?token=" + saved.getToken()), eq(30L));
    }

    @Test
    void requestReset_unknownEmail_doesNothingAndDoesNotFail() {
        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> passwordResetService.requestReset("ghost@example.com"));

        verifyNoInteractions(tokenRepository, mailService);
    }

    @Test
    void resetPassword_validToken_changesPasswordAndConsumesToken() {
        PasswordResetToken token = PasswordResetToken.builder()
                .token("abc").user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();
        when(tokenRepository.findByToken("abc")).thenReturn(Optional.of(token));
        when(passwordEncoder.encode("newPassword1")).thenReturn("hashed");

        passwordResetService.resetPassword("abc", "newPassword1");

        assertEquals("hashed", user.getPassword());
        verify(userRepository).save(user);
        verify(tokenRepository).delete(token);
    }

    @Test
    void resetPassword_unknownToken_throws() {
        when(tokenRepository.findByToken("nope")).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> passwordResetService.resetPassword("nope", "newPassword1"));
        verify(userRepository, never()).save(any());
    }

    @Test
    void resetPassword_expiredToken_throwsAndRemovesToken() {
        PasswordResetToken token = PasswordResetToken.builder()
                .token("old").user(user)
                .createdAt(LocalDateTime.now().minusHours(2))
                .expiresAt(LocalDateTime.now().minusHours(1))
                .build();
        when(tokenRepository.findByToken("old")).thenReturn(Optional.of(token));

        assertThrows(TokenExpiredException.class, () -> passwordResetService.resetPassword("old", "newPassword1"));
        verify(tokenRepository).delete(token);
        verify(userRepository, never()).save(any());
        assertEquals("old", user.getPassword());
    }
}
