package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.InvalidCredentialsException;
import com.example.userbridge.domain.user.exception.UserNotActivatedException;
import com.example.userbridge.infrastructure.repository.UserRepository;
import com.example.userbridge.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new InvalidCredentialsException("Nieprawidłowy e-mail lub hasło"));
        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Nieprawidłowy e-mail lub hasło");
        }
        if (!user.isEnabled()) {
            throw new UserNotActivatedException("Konto nie zostało jeszcze aktywowane. Sprawdź skrzynkę e-mail.");
        }
        return jwtService.createToken(user.getEmail());
    }
}
