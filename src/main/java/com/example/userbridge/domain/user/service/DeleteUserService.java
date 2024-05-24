package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.UserNotFoundException;
import com.example.userbridge.infrastructure.repository.ConfirmationTokenRepository;
import com.example.userbridge.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public DeleteUserService(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        confirmationTokenRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
