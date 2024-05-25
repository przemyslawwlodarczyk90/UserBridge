//package com.example.userbridge.infrastructure.security;
//
//import com.example.userbridge.domain.user.entity.User;
//import com.example.userbridge.infrastructure.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@EnableJpaRepositories(basePackages = "com.example.userbridge.infrastructure.repository")
//@Import(CustomUserDetailsServiceTest.TestConfig.class)
//class CustomUserDetailsServiceTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private JwtService jwtService;
//
//    @MockBean
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Configuration
//    static class TestConfig {
//        @Bean
//        public UserDetailsService userDetailsService() {
//            return email -> {
//                User user = new User();
//                user.setEmail(email);
//                user.setPassword("password");
//                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
//            };
//        }
//    }
//
//    @BeforeEach
//    @Transactional
//    public void setUp() {
//        User user = User.builder()
//                .firstName("Jan")
//                .lastName("Kowalski")
//                .email("jan.kowalski@example.com")
//                .password("password")
//                .enabled(true)
//                .build();
//        userRepository.save(user);
//
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), new ArrayList<>()
//        );
//
//        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
//        when(jwtService.extractEmail(anyString())).thenReturn(user.getEmail());
//        when(jwtService.validateToken(anyString())).thenReturn(true);
//    }
//
//    @Test
//    @Transactional
//    void testFilter_ValidToken() throws Exception {
//        String token = "valid-token";
//
//        when(jwtService.validateToken(token)).thenReturn(true);
//
//        mockMvc.perform(get("/api/some-secured-endpoint")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Transactional
//    void testFilter_InvalidToken() throws Exception {
//        String token = "invalid-token";
//
//        when(jwtService.validateToken(token)).thenReturn(false);
//
//        mockMvc.perform(get("/api/some-secured-endpoint")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    @Transactional
//    void testFilter_MissingToken() throws Exception {
//        mockMvc.perform(get("/api/some-secured-endpoint")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized());
//    }
//}
