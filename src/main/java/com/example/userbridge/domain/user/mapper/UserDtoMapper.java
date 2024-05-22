package com.example.userbridge.domain.user.mapper;

import com.example.userbridge.domain.user.dto.UserDto;
import org.springframework.stereotype.Component;
import com.example.userbridge.domain.user.entity.User;

@Component
public class UserDtoMapper {

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getStreet(),
                user.getPostalCode(),
                user.getCity()
        );
    }

    public User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .email(userDto.email())
                .phoneNumber(userDto.phoneNumber())
                .street(userDto.street())
                .postalCode(userDto.postalCode())
                .city(userDto.city())
                .build();
    }
}
