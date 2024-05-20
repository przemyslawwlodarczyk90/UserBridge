package userbridge.domain.user.service;


import org.springframework.stereotype.Service;
import userbridge.domain.user.entity.User;
import userbridge.infrastructure.repository.UserRepository;

import java.util.UUID;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;


    public DeleteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}