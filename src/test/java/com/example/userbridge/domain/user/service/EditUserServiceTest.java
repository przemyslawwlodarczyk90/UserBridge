package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.UserNotFoundException;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EditUserService editUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEditUserSuccess() {
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

        User user = User.builder()
                .id(id)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna.nowak@example.com")
                .phoneNumber("987654321")
                .street("ul. Nowogrodzka 15")
                .postalCode("00-511")
                .city("Warszawa")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        editUserService.edit(userDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals(userDto.getId(), capturedUser.getId());
        assertEquals(userDto.getFirstName(), capturedUser.getFirstName());
        assertEquals(userDto.getLastName(), capturedUser.getLastName());
        assertEquals(userDto.getEmail(), capturedUser.getEmail());
        assertEquals(userDto.getPhoneNumber(), capturedUser.getPhoneNumber());
        assertEquals(userDto.getStreet(), capturedUser.getStreet());
        assertEquals(userDto.getPostalCode(), capturedUser.getPostalCode());
        assertEquals(userDto.getCity(), capturedUser.getCity());
    }

    @Test
    void testEditUserNotFound() {
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

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> editUserService.edit(userDto));
    }
}