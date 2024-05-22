package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.UserNotFoundException;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;

    public DeleteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
