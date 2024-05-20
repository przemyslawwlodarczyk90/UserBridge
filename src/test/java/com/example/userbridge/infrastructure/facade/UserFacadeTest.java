package com.example.userbridge.infrastructure.facade;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.service.DeleteUserService;
import com.example.userbridge.domain.user.service.EditUserService;
import com.example.userbridge.domain.user.service.LoginService;
import com.example.userbridge.domain.user.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserFacadeTest {

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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
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

        userFacade.registerUser(userDto, password);

        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);

        verify(registrationService).register(userDtoCaptor.capture(), passwordCaptor.capture());

        assertEquals(userDto, userDtoCaptor.getValue());
        assertEquals(password, passwordCaptor.getValue());
    }

    @Test
    public void testLoginUser() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jan.kowalski@example.com");
        loginDto.setPassword("password123");

        when(loginService.login(any(LoginDto.class))).thenReturn("JWT_TOKEN");

        String token = userFacade.loginUser(loginDto);

        ArgumentCaptor<LoginDto> loginDtoCaptor = ArgumentCaptor.forClass(LoginDto.class);
        verify(loginService).login(loginDtoCaptor.capture());

        assertEquals(loginDto, loginDtoCaptor.getValue());
        assertEquals("JWT_TOKEN", token);
    }

    @Test
    public void testEditUser() {
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

        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);
        verify(editUserService).edit(userDtoCaptor.capture());

        assertEquals(userDto, userDtoCaptor.getValue());
    }

    @Test
    public void testDeleteUser() {
        UUID userId = UUID.randomUUID();

        userFacade.deleteUser(userId);

        ArgumentCaptor<UUID> userIdCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(deleteUserService).delete(userIdCaptor.capture());

        assertEquals(userId, userIdCaptor.getValue());
    }
}
