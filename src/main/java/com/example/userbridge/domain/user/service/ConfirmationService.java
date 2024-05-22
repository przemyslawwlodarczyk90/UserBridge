package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.entity.ConfirmationToken;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.TokenExpiredException;
import com.example.userbridge.domain.user.exception.TokenNotFoundException;
import com.example.userbridge.infrastructure.repository.ConfirmationTokenRepository;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationService {
    private final ConfirmationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public ConfirmationService(ConfirmationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token has expired");
        }

        User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        tokenRepository.delete(confirmationToken);
    }
}
