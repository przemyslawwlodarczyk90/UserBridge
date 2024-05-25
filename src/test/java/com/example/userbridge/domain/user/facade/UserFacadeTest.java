package com.example.userbridge.domain.user.facade;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

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

   @Mock
   private UserService userService;

   @InjectMocks
   private UserFacade userFacade;

   @BeforeEach
   void setUp() {
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
      String password = "password";

      doNothing().when(registrationService).register(userDto, password);

      userFacade.registerUser(userDto, password);

      verify(registrationService, times(1)).register(userDto, password);
   }

   @Test
   void testLoginUser() {
      LoginDto loginDto = new LoginDto("jan.kowalski@example.com", "password");
      String expectedToken = "token";

      when(loginService.login(loginDto)).thenReturn(expectedToken);

      String actualToken = userFacade.loginUser(loginDto);

      assertEquals(expectedToken, actualToken);
      verify(loginService, times(1)).login(loginDto);
   }

   @Test
   void testEditUser() {
      Long userId = 1L;
      UserDto userDto = UserDto.builder()
              .id(userId)
              .firstName("Jan")
              .lastName("Kowalski")
              .email("jan.kowalski@example.com")
              .phoneNumber("123456789")
              .street("ul. Marszałkowska 123")
              .postalCode("00-001")
              .city("Warszawa")
              .build();

      doNothing().when(editUserService).edit(userId, userDto);

      userFacade.editUser(userId, userDto);

      verify(editUserService, times(1)).edit(userId, userDto);
   }

   @Test
   void testDeleteUser() {
      Long userId = 1L;

      doNothing().when(deleteUserService).delete(userId);

      userFacade.deleteUser(userId);

      verify(deleteUserService, times(1)).delete(userId);
   }

   @Test
   void testGetAllUsers() {
      UserDto userDto1 = UserDto.builder()
              .id(1L)
              .firstName("Jan")
              .lastName("Kowalski")
              .email("jan.kowalski@example.com")
              .phoneNumber("123456789")
              .street("ul. Marszałkowska 123")
              .postalCode("00-001")
              .city("Warszawa")
              .build();

      UserDto userDto2 = UserDto.builder()
              .id(2L)
              .firstName("Anna")
              .lastName("Nowak")
              .email("anna.nowak@example.com")
              .phoneNumber("987654321")
              .street("ul. Piękna 456")
              .postalCode("00-002")
              .city("Kraków")
              .build();

      when(userService.getAllUsers()).thenReturn(List.of(userDto1, userDto2));

      List<UserDto> actualUsers = userFacade.getAllUsers();

      assertEquals(2, actualUsers.size());
      assertEquals(userDto1, actualUsers.get(0));
      assertEquals(userDto2, actualUsers.get(1));

      verify(userService, times(1)).getAllUsers();
   }
}
