package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EditUserServiceTest {

   @Mock
   private UserRepository userRepository;

   @InjectMocks
   private EditUserService editUserService;

   private User existingUser;

   @BeforeEach
   void setUp() {
      MockitoAnnotations.openMocks(this);
      existingUser = new User(1L, "John", "Doe", "password", "john.doe@example.com", "123456789", "Street 1", "00-001", "City", true, null);
   }

   @Test
   void testEditUserEmailCannotBeChanged() {

      UserDto userDto = new UserDto(1L, "John", "Doe", "new.email@example.com", "123456789", "Street 1", "00-001", "City");

      when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));


      assertThrows(IllegalArgumentException.class, () -> editUserService.edit(1L, userDto));
   }

   @Test
   void testEditUser() {

      UserDto userDto = new UserDto(1L, "John", "Doe", "john.doe@example.com", "987654321", "New Street", "00-002", "New City");

      when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));


      editUserService.edit(1L, userDto);


      verify(userRepository, times(1)).save(existingUser);
      verify(userRepository, times(1)).findById(1L);

      assertEquals("987654321", existingUser.getPhoneNumber());
      assertEquals("New Street", existingUser.getStreet());
      assertEquals("00-002", existingUser.getPostalCode());
      assertEquals("New City", existingUser.getCity());
      assertEquals("john.doe@example.com", existingUser.getEmail());
   }
}
