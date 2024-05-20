package com.example.userbridge.domain.user.service;


import com.example.userbridge.domain.user.dto.UserDto;
import org.springframework.stereotype.Service;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.infrastructure.repository.UserRepository;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserDtoMapper dtoMapper;


    public RegistrationService(UserRepository userRepository, UserDtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
    }

    public void register(UserDto userDto) {
        User user = dtoMapper.toUser(userDto);
        userRepository.save(user);
    }
}
