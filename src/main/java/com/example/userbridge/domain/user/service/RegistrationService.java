package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.infrastructure.repository.UserRepository;
import com.example.userbridge.infrastructure.service.MailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserDtoMapper dtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public RegistrationService(UserRepository userRepository, UserDtoMapper dtoMapper, PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public void register(UserDto userDto, String password) {
        User user = dtoMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);


        String token = generateConfirmationToken(user);
        String confirmationLink = "http://localhost:8080/api/users/confirm?token=" + token;


        String subject = "Confirm your registration";
        String body = "Click the following link to confirm your registration: " + confirmationLink;
        mailService.sendConfirmationEmail(user.getEmail(), subject, body);
    }

    private String generateConfirmationToken(User user) {

        return "dummy-token";
    }
}
