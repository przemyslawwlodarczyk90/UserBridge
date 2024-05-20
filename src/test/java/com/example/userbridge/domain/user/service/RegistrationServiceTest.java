package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class RegistrationServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();
    private final RegistrationService registrationService = new RegistrationService(userRepository, userDtoMapper);

    @Test
    void testRegister() {
        UserDto userDto = UserDto.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .street("ul. Marszałkowska 123")
                .postalCode("00-001")
                .city("Warszawa")
                .build();
        String password = "password123";

        registrationService.register(userDto, password);

        verify(userRepository).save(any(User.class));
    }
}
