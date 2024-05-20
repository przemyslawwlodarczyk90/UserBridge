package com.example.userbridge.infrastructure.facade;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.service.DeleteUserService;
import com.example.userbridge.domain.user.service.EditUserService;
import com.example.userbridge.domain.user.service.LoginService;
import com.example.userbridge.domain.user.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserFacadeTest {

    @Mock
    private RegistrationService registrationService;

    @Mock
    private LoginService loginService;

    @Mock
    private EditUserService editUserService;

    @Mock
    private DeleteUserService deleteUserService;

    @InjectMocks
    private UserFacade userFacade;

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

        userFacade.registerUser(userDto);

        verify(registrationService, times(1)).register(userDto);
    }

    @Test
    void testLoginUser() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jan.kowalski@example.com");
        loginDto.setPassword("password123");

        when(loginService.login(loginDto)).thenReturn("JWT_TOKEN");

        String token = userFacade.loginUser(loginDto);

        assertEquals("JWT_TOKEN", token);
        verify(loginService, times(1)).login(loginDto);
    }

    @Test
    void testEditUser() {
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

        userFacade.editUser(userDto);

        verify(editUserService, times(1)).edit(userDto);
    }

    @Test
    void testDeleteUser() {
        UUID userId = UUID.randomUUID();

        userFacade.deleteUser(userId);

        verify(deleteUserService, times(1)).delete(userId);
    }
}
