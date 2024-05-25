package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.ConfirmationToken;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.EmailAlreadyInUseException;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.infrastructure.repository.ConfirmationTokenRepository;
import com.example.userbridge.infrastructure.repository.UserRepository;
import com.example.userbridge.infrastructure.service.MailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository tokenRepository;
    private final UserDtoMapper dtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public RegistrationService(UserRepository userRepository, ConfirmationTokenRepository tokenRepository,
                               UserDtoMapper dtoMapper, PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.dtoMapper = dtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public void register(UserDto userDto, String password) {
        // Sprawdzenie, czy istnieje aktywny użytkownik z tym adresem email
        userRepository.findByEmailAndEnabled(userDto.email(), true)
                .ifPresent(user -> {
                    throw new EmailAlreadyInUseException("Email is already in use by an active user");
                });

        User user = dtoMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(false); // Użytkownik jest nieaktywny po rejestracji
        userRepository.save(user);

        String token = generateConfirmationToken(user);
        String confirmationLink = "http://localhost:8080/api/users/confirm?token=" + token;

        String subject = "Confirm your registration";
        mailService.sendConfirmationEmail(user.getEmail(), subject, user.getFirstName(), user.getLastName(), confirmationLink);
    }

    private String generateConfirmationToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .user(user)
                .build();
        tokenRepository.save(confirmationToken);
        return token;
    }
}
