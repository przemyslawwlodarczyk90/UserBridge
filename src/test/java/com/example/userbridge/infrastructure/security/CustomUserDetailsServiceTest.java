package com.example.userbridge.infrastructure.security;

import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
 class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .password("password")
                .enabled(true)
                .build();
        userRepository.save(user);
    }

    @Test
    public void testLoadUserByUsername_Success() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("jan.kowalski@example.com");
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("jan.kowalski@example.com");
        assertThat(userDetails.getPassword()).isEqualTo("password");
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent@example.com");
        });
    }
}
