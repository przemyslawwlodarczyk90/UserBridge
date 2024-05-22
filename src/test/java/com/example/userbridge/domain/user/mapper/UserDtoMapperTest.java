package com.example.userbridge.domain.user.mapper;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class UserDtoMapperTest {

    private UserDtoMapper userDtoMapper;

    @BeforeEach
    public void setUp() {
        userDtoMapper = new UserDtoMapper();
    }

    @Test
     void testToUserDto() {
        User user = User.builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .street("ul. Marszałkowska 123")
                .postalCode("00-001")
                .city("Warszawa")
                .build();

        UserDto userDto = userDtoMapper.toUserDto(user);

        assertEquals(user.getId(), userDto.id());
        assertEquals(user.getFirstName(), userDto.firstName());
        assertEquals(user.getLastName(), userDto.lastName());
        assertEquals(user.getEmail(), userDto.email());
        assertEquals(user.getPhoneNumber(), userDto.phoneNumber());
        assertEquals(user.getStreet(), userDto.street());
        assertEquals(user.getPostalCode(), userDto.postalCode());
        assertEquals(user.getCity(), userDto.city());
    }

    @Test
     void testToUser() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .street("ul. Marszałkowska 123")
                .postalCode("00-001")
                .city("Warszawa")
                .build();

        User user = userDtoMapper.toUser(userDto);

        assertEquals(userDto.id(), user.getId());
        assertEquals(userDto.firstName(), user.getFirstName());
        assertEquals(userDto.lastName(), user.getLastName());
        assertEquals(userDto.email(), user.getEmail());
        assertEquals(userDto.phoneNumber(), user.getPhoneNumber());
        assertEquals(userDto.street(), user.getStreet());
        assertEquals(userDto.postalCode(), user.getPostalCode());
        assertEquals(userDto.city(), user.getCity());
    }
}
