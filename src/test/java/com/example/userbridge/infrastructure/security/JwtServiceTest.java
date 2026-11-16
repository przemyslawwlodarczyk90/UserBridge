package com.example.userbridge.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "test-secret-key-test-secret-key");
        ReflectionTestUtils.setField(jwtService, "expirationDays", 1L);
        ReflectionTestUtils.setField(jwtService, "issuer", "UserBridge");
    }

    @Test
    void freshTokenIsValidImmediately_regardlessOfServerTimezone() {
        String token = jwtService.createToken("jan@example.com");

        assertTrue(jwtService.validateToken(token));
        assertEquals("jan@example.com", jwtService.extractEmail(token));
    }

    @Test
    void tamperedTokenIsRejected() {
        String token = jwtService.createToken("jan@example.com");
        assertFalse(jwtService.validateToken(token + "x"));
    }
}
