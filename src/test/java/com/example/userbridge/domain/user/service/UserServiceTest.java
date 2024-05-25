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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {

        User user1 = new User(1L, "John", "Doe", "password", "john.doe@example.com", "123456789", "Street 1", "00-001", "City", true, null);
        User user2 = new User(2L, "Jane", "Doe", "password", "jane.doe@example.com", "987654321", "Street 2", "00-002", "City", true, null);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        UserDto userDto1 = new UserDto(1L, "John", "Doe", "john.doe@example.com", "123456789", "Street 1", "00-001", "City");
        UserDto userDto2 = new UserDto(2L, "Jane", "Doe", "jane.doe@example.com", "987654321", "Street 2", "00-002", "City");
        when(userDtoMapper.toUserDto(user1)).thenReturn(userDto1);
        when(userDtoMapper.toUserDto(user2)).thenReturn(userDto2);


        List<UserDto> userDtos = userService.getAllUsers();


        assertEquals(2, userDtos.size());
        assertEquals(userDto1, userDtos.get(0));
        assertEquals(userDto2, userDtos.get(1));

        verify(userRepository, times(1)).findAll();
        verify(userDtoMapper, times(1)).toUserDto(user1);
        verify(userDtoMapper, times(1)).toUserDto(user2);
    }
}
