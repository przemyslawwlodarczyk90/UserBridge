//package com.example.userbridge.domain.user.service;
//
//import com.example.userbridge.domain.user.dto.UserDto;
//import com.example.userbridge.domain.user.entity.User;
//import com.example.userbridge.domain.user.mapper.UserDtoMapper;
//import com.example.userbridge.infrastructure.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.mockito.Mockito.*;
//
//class RegistrationServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private UserDtoMapper userDtoMapper;
//
//    @InjectMocks
//    private RegistrationService registrationService;
//
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        passwordEncoder = new BCryptPasswordEncoder();
//        registrationService = new RegistrationService(userRepository, userDtoMapper, passwordEncoder);
//    }
//
//    @Test
//    void testRegister() {
//        // Given
//        UserDto userDto = new UserDto();
//        userDto.setEmail("jan.kowalski@example.com");
//        userDto.setFirstName("Jan");
//        userDto.setLastName("Kowalski");
//
//        User user = new User();
//        user.setEmail("jan.kowalski@example.com");
//        user.setFirstName("Jan");
//        user.setLastName("Kowalski");
//
//        when(userDtoMapper.toUser(any(UserDto.class))).thenReturn(user);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        // When
//        registrationService.register(userDto, "haslo123");
//
//        // Then
//        verify(userDtoMapper, times(1)).toUser(userDto);
//        verify(userRepository, times(1)).save(user);
//    }
//}
