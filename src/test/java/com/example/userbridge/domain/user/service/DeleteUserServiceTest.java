package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.UserNotFoundException;
import com.example.userbridge.infrastructure.repository.ConfirmationTokenRepository;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteUserServiceTest {

   @Mock
   private UserRepository userRepository;

   @Mock
   private ConfirmationTokenRepository confirmationTokenRepository;

   @InjectMocks
   private DeleteUserService deleteUserService;

   @BeforeEach
   public void setUp() {
      MockitoAnnotations.openMocks(this);
   }

   @Test
   void testDeleteUserSuccess() {
      Long userId = 1L;
      User user = new User();
      user.setId(userId);

      when(userRepository.findById(userId)).thenReturn(Optional.of(user));

      deleteUserService.delete(userId);

      verify(userRepository).findById(userId);
      verify(confirmationTokenRepository).deleteByUser(user);
      verify(userRepository).delete(user);
   }

   @Test
   void testDeleteUserNotFound() {
      Long userId = 1L;

      when(userRepository.findById(userId)).thenReturn(Optional.empty());

      assertThrows(UserNotFoundException.class, () -> deleteUserService.delete(userId));

      verify(userRepository).findById(userId);
      verify(confirmationTokenRepository, never()).deleteByUser(any(User.class));
      verify(userRepository, never()).delete(any(User.class));
   }
}