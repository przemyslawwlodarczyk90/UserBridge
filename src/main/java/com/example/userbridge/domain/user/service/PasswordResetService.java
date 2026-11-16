package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.entity.PasswordResetToken;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.TokenExpiredException;
import com.example.userbridge.domain.user.exception.TokenNotFoundException;
import com.example.userbridge.infrastructure.repository.PasswordResetTokenRepository;
import com.example.userbridge.infrastructure.repository.UserRepository;
import com.example.userbridge.infrastructure.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * "Forgot password" flow:
 * <ol>
 *     <li>{@link #requestReset(String)} - generates a one-time token and e-mails a link to the frontend</li>
 *     <li>{@link #resetPassword(String, String)} - validates the token and stores the new (hashed) password</li>
 * </ol>
 * The request step never reveals whether an account exists (protection against user enumeration).
 */
@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Value("${app.password-reset.expiration-minutes:30}")
    private long expirationMinutes = 30;

    public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository tokenRepository,
                                PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Transactional
    public void requestReset(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            // only the newest link is valid
            tokenRepository.deleteByUser(user);

            String token = UUID.randomUUID().toString();
            tokenRepository.save(PasswordResetToken.builder()
                    .token(token)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(expirationMinutes))
                    .user(user)
                    .build());

            String resetLink = frontendUrl + "/reset-password?token=" + token;
            mailService.sendPasswordResetEmail(user.getEmail(), user.getFirstName(), resetLink, expirationMinutes);
        });
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Reset link is invalid or has already been used"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new TokenExpiredException("Reset link has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }
}
