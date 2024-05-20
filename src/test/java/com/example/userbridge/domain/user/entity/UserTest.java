package com.example.userbridge.domain.user.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        UUID id = UUID.randomUUID();
        String firstName = "Jan";
        String lastName = "Kowalski";
        String email = "jan.kowalski@example.com";
        String phoneNumber = "123456789";
        String street = "ul. Marszałkowska 123";
        String postalCode = "00-001";
        String city = "Warszawa";

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setStreet(street);
        user.setPostalCode(postalCode);
        user.setCity(city);

        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(street, user.getStreet());
        assertEquals(postalCode, user.getPostalCode());
        assertEquals(city, user.getCity());
    }

    @Test
    void testUserBuilder() {
        UUID id = UUID.randomUUID();
        String firstName = "Anna";
        String lastName = "Nowak";
        String email = "anna.nowak@example.com";
        String phoneNumber = "987654321";
        String street = "ul. Nowogrodzka 15";
        String postalCode = "00-511";
        String city = "Warszawa";

        User user = User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .build();

        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(street, user.getStreet());
        assertEquals(postalCode, user.getPostalCode());
        assertEquals(city, user.getCity());
    }

    @Test
    void testUserEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        String firstName = "Krzysztof";
        String lastName = "Wiśniewski";
        String email = "krzysztof.wisniewski@example.com";
        String phoneNumber = "567890123";
        String street = "ul. Świętokrzyska 21";
        String postalCode = "00-002";
        String city = "Warszawa";

        User user1 = User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .build();

        User user2 = User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .build();

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
