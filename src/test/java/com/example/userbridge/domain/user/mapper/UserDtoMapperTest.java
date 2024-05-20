package com.example.userbridge.domain.user.mapper;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoMapperTest {

    private UserDtoMapper userDtoMapper;

    @BeforeEach
    void setUp() {
        userDtoMapper = new UserDtoMapper();
    }

    @Test
    void testToUserDto() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .street("ul. Marszałkowska 123")
                .postalCode("00-001")
                .city("Warszawa")
                .build();

        UserDto userDto = userDtoMapper.toUserDto(user);

        assertEquals(id, userDto.getId());
        assertEquals("Jan", userDto.getFirstName());
        assertEquals("Kowalski", userDto.getLastName());
        assertEquals("jan.kowalski@example.com", userDto.getEmail());
        assertEquals("123456789", userDto.getPhoneNumber());
        assertEquals("ul. Marszałkowska 123", userDto.getStreet());
        assertEquals("00-001", userDto.getPostalCode());
        assertEquals("Warszawa", userDto.getCity());
    }

    @Test
    void testToUser() {
        UUID id = UUID.randomUUID();
        UserDto userDto = UserDto.builder()
                .id(id)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna.nowak@example.com")
                .phoneNumber("987654321")
                .street("ul. Nowogrodzka 15")
                .postalCode("00-511")
                .city("Warszawa")
                .build();

        User user = userDtoMapper.toUser(userDto);

        assertEquals(id, user.getId());
        assertEquals("Anna", user.getFirstName());
        assertEquals("Nowak", user.getLastName());
        assertEquals("anna.nowak@example.com", user.getEmail());
        assertEquals("987654321", user.getPhoneNumber());
        assertEquals("ul. Nowogrodzka 15", user.getStreet());
        assertEquals("00-511", user.getPostalCode());
        assertEquals("Warszawa", user.getCity());
    }
}