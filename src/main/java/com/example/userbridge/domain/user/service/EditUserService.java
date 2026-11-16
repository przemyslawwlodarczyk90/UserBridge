package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.UserNotActivatedException;
import com.example.userbridge.domain.user.exception.UserNotFoundException;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EditUserService {
    private final UserRepository userRepository;

    public EditUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void edit(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Nie znaleziono użytkownika"));

        if (!user.isEnabled()) {
            throw new UserNotActivatedException("Konto nie jest aktywne");
        }


        if (!userDto.email().equals(user.getEmail())) {
            throw new IllegalArgumentException("Nie można zmienić adresu e-mail");
        }

        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setPhoneNumber(userDto.phoneNumber());
        user.setStreet(userDto.street());
        user.setPostalCode(userDto.postalCode());
        user.setCity(userDto.city());
        userRepository.save(user);
    }
}

