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
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setStreet(userDto.getStreet());
        user.setPostalCode(userDto.getPostalCode());
        user.setCity(userDto.getCity());
        userRepository.save(user);
    }
}
