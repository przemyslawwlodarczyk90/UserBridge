package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserDtoMapper dtoMapper;
    private final PasswordEncoder passwordEncoder;


    public RegistrationService(UserRepository userRepository, UserDtoMapper dtoMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserDto userDto, String password) {
        User user = dtoMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}