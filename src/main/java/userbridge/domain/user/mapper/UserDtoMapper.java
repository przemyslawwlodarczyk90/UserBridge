package userbridge.domain.user.mapper;

import org.springframework.stereotype.Component;
import userbridge.domain.user.dto.UserDto;
import userbridge.domain.user.entity.User;

@Component
public class UserDtoMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .street(user.getStreet())
                .postalCode(user.getPostalCode())
                .city(user.getCity())
                .build();
    }

    public User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .street(userDto.getStreet())
                .postalCode(userDto.getPostalCode())
                .city(userDto.getCity())
                .build();
    }
}