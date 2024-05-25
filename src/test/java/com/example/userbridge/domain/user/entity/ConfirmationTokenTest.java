package com.example.userbridge.domain.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmationTokenTest {

    private User user;
    private ConfirmationToken confirmationToken;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .password("password")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .street("ul. Marszałkowska 123")
                .postalCode("00-001")
                .city("Warszawa")
                .enabled(true)
                .build();

        confirmationToken = ConfirmationToken.builder()
                .id(1L)
                .token("sample-token")
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .user(user)
                .build();
    }

    @Test
    void testConfirmationTokenCreation() {
        assertNotNull(confirmationToken);
        assertEquals(1L, confirmationToken.getId());
        assertEquals("sample-token", confirmationToken.getToken());
        assertNotNull(confirmationToken.getCreatedAt());
        assertNotNull(confirmationToken.getExpiresAt());
        assertEquals(user, confirmationToken.getUser());
    }

    @Test
    void testTokenExpiration() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        confirmationToken.setExpiresAt(futureDate);

        assertEquals(futureDate, confirmationToken.getExpiresAt());
    }

    @Test
    void testUserAssociation() {
        User newUser = User.builder()
                .id(2L)
                .firstName("Anna")
                .lastName("Nowak")
                .password("password")
                .email("anna.nowak@example.com")
                .phoneNumber("987654321")
                .street("ul. Piękna 456")
                .postalCode("00-002")
                .city("Kraków")
                .enabled(true)
                .build();

        confirmationToken.setUser(newUser);

        assertEquals(newUser, confirmationToken.getUser());
    }
}
