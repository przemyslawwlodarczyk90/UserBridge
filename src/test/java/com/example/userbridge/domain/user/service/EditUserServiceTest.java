package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.UserNotFoundException;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EditUserServiceTest {

   @Mock
   private UserRepository userRepository;

   @InjectMocks
   private EditUserService editUserService;

   @BeforeEach
   public void setUp() {
      MockitoAnnotations.openMocks(this);
   }

   @Test
   void testEditUserSuccess() {
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

      User user = new User();
      user.setId(userDto.id());

      when(userRepository.findById(userId)).thenReturn(Optional.of(user));

      editUserService.edit(userId, userDto);

      verify(userRepository).findById(userId);
      verify(userRepository).save(user);

      Assertions.assertEquals(userDto.firstName(), user.getFirstName());
      Assertions.assertEquals(userDto.lastName(), user.getLastName());
      Assertions.assertEquals(userDto.email(), user.getEmail());
      Assertions.assertEquals(userDto.phoneNumber(), user.getPhoneNumber());
      Assertions.assertEquals(userDto.street(), user.getStreet());
      Assertions.assertEquals(userDto.postalCode(), user.getPostalCode());
      Assertions.assertEquals(userDto.city(), user.getCity());
   }

   @Test
   void testEditUserNotFound() {
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

      when(userRepository.findById(userId)).thenReturn(Optional.empty());

      assertThrows(UserNotFoundException.class, () -> editUserService.edit(userId, userDto));

      verify(userRepository).findById(userId);
      verify(userRepository, never()).save(any(User.class));
   }
}
