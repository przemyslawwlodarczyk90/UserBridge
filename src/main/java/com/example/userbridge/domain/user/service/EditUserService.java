package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.UserNotFoundException;

import com.example.userbridge.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EditUserService {
    private final UserRepository userRepository;

    public EditUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void edit(UserDto userDto) {
        User user = userRepository.findById(userDto.id())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setPhoneNumber(userDto.phoneNumber());
        user.setStreet(userDto.street());
        user.setPostalCode(userDto.postalCode());
        user.setCity(userDto.city());
        userRepository.save(user);
    }
}