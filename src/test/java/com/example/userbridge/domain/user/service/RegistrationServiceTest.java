package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoMapper dtoMapper;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        UserDto userDto = UserDto.builder()
                .id(UUID.randomUUID())
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .street("ul. Marszałkowska 123")
                .postalCode("00-001")
                .city("Warszawa")
                .build();

        User user = User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .street(userDto.getStreet())
                .postalCode(userDto.getPostalCode())
                .city(userDto.getCity())
                .build();

        when(dtoMapper.toUser(userDto)).thenReturn(user);

        registrationService.register(userDto);

        verify(userRepository, times(1)).save(user);
    }
}