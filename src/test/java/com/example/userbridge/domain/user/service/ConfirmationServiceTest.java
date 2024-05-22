package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.entity.ConfirmationToken;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.TokenExpiredException;
import com.example.userbridge.domain.user.exception.TokenNotFoundException;
import com.example.userbridge.infrastructure.repository.ConfirmationTokenRepository;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

 class ConfirmationServiceTest {

    @Mock
    private ConfirmationTokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConfirmationService confirmationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testConfirmTokenSuccess() {
        String token = "valid-token";
        User user = new User();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .user(user)
                .build();

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(confirmationToken));

        confirmationService.confirmToken(token);

        verify(userRepository).save(user);
        verify(tokenRepository).delete(confirmationToken);
        Assertions.assertTrue(user.isEnabled());
    }

    @Test
     void testConfirmTokenNotFound() {
        String token = "invalid-token";

        when(tokenRepository.findByToken(token)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> confirmationService.confirmToken(token));
    }

    @Test
     void testConfirmTokenExpired() {
        String token = "expired-token";
        User user = new User();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now().minusDays(2))
                .expiresAt(LocalDateTime.now().minusDays(1))
                .user(user)
                .build();

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(confirmationToken));

        assertThrows(TokenExpiredException.class, () -> confirmationService.confirmToken(token));
    }
}
