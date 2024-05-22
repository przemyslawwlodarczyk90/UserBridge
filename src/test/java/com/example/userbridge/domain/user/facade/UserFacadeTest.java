package com.example.userbridge.domain.user.facade;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testRegisterUser() {
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
     void testLoginUser() {
        LoginDto loginDto = new LoginDto("jan.kowalski@example.com", "password123");

        when(loginService.login(any(LoginDto.class))).thenReturn("JWT_TOKEN");

        String token = userFacade.loginUser(loginDto);

        ArgumentCaptor<LoginDto> loginDtoCaptor = ArgumentCaptor.forClass(LoginDto.class);
        verify(loginService).login(loginDtoCaptor.capture());

        assertEquals(loginDto, loginDtoCaptor.getValue());
        assertEquals("JWT_TOKEN", token);
    }

    @Test
     void testEditUser() {
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

        userFacade.editUser(userDto);

        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);
        verify(editUserService).edit(userDtoCaptor.capture());

        assertEquals(userDto, userDtoCaptor.getValue());
    }

    @Test
     void testDeleteUser() {
        Long userId = 1L;

        userFacade.deleteUser(userId);

        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        verify(deleteUserService).delete(userIdCaptor.capture());

        assertEquals(userId, userIdCaptor.getValue());
    }
}
