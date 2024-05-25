package com.example.userbridge.domain.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private ConfirmationToken confirmationToken;

    @BeforeEach
    void setUp() {
        confirmationToken = ConfirmationToken.builder()
                .id(1L)
                .token("sample-token")
                .build();

        Set<ConfirmationToken> confirmationTokens = new HashSet<>();
        confirmationTokens.add(confirmationToken);

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
                .confirmationTokens(confirmationTokens)
                .build();
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Jan", user.getFirstName());
        assertEquals("Kowalski", user.getLastName());
        assertEquals("password", user.getPassword());
        assertEquals("jan.kowalski@example.com", user.getEmail());
        assertEquals("123456789", user.getPhoneNumber());
        assertEquals("ul. Marszałkowska 123", user.getStreet());
        assertEquals("00-001", user.getPostalCode());
        assertEquals("Warszawa", user.getCity());
        assertTrue(user.isEnabled());
        assertEquals(1, user.getConfirmationTokens().size());
    }

    @Test
    void testAddConfirmationToken() {
        ConfirmationToken newToken = ConfirmationToken.builder()
                .id(2L)
                .token("new-token")
                .build();

        user.getConfirmationTokens().add(newToken);

        assertEquals(2, user.getConfirmationTokens().size());
    }

    @Test
    void testRemoveConfirmationToken() {
        user.getConfirmationTokens().remove(confirmationToken);

        assertEquals(0, user.getConfirmationTokens().size());
    }
}
