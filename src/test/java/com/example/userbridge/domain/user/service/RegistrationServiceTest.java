package com.example.userbridge.domain.user.service;

import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.ConfirmationToken;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.infrastructure.repository.ConfirmationTokenRepository;
import com.example.userbridge.infrastructure.repository.UserRepository;
import com.example.userbridge.infrastructure.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

 class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConfirmationTokenRepository tokenRepository;

    @Mock
    private UserDtoMapper dtoMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @InjectMocks
    private RegistrationService registrationService;

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

        User user = new User();
        user.setEmail(userDto.email());
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());

        when(dtoMapper.toUser(userDto)).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        registrationService.register(userDto, password);

        verify(userRepository).save(user);
        verify(tokenRepository).save(any(ConfirmationToken.class));
        verify(mailService).sendConfirmationEmail(eq(user.getEmail()), eq("Confirm your registration"), eq(user.getFirstName()), eq(user.getLastName()), anyString());

        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
     void testGenerateConfirmationTokenCalledWithinRegister() {
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

        User user = new User();
        user.setEmail(userDto.email());
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());

        when(dtoMapper.toUser(userDto)).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        registrationService.register(userDto, password);

        ArgumentCaptor<ConfirmationToken> captor = ArgumentCaptor.forClass(ConfirmationToken.class);
        verify(tokenRepository).save(captor.capture());

        ConfirmationToken savedToken = captor.getValue();
        assertEquals(user, savedToken.getUser());
        assertEquals(user.getEmail(), savedToken.getUser().getEmail());
    }
}
