package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.InvalidCredentialsException;
import com.example.userbridge.infrastructure.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        if (user.getPassword().equals(loginDto.getPassword())) {
            return "JWT_TOKEN";
        }
        throw new InvalidCredentialsException("Invalid email or password");
    }
}
