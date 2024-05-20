package com.example.userbridge.domain.user.service;


import com.example.userbridge.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.infrastructure.repository.UserRepository;

import java.util.UUID;

@Service
public class DeleteUserService {

    private final UserRepository userRepository;


    public DeleteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}