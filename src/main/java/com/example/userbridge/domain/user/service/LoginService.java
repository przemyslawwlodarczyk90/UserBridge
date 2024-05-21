package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.InvalidCredentialsException;
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
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return jwtService.createToken(user.getEmail());
        }
        throw new InvalidCredentialsException("Invalid email or password");
    }
}
