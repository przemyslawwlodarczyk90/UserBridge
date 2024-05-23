package com.example.userbridge.domain.user.facade;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.service.EditUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UserFacadeTest {

   @Mock
   private EditUserService editUserService;

   @InjectMocks
   private UserFacade userFacade;

   @BeforeEach
   void setUp() {
      MockitoAnnotations.openMocks(this);
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
}
