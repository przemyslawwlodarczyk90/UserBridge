package com.example.userbridge.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false)
    private UUID id;

    private String firstName;
    private String lastName;

    private String password;
    private String email;
    private String phoneNumber;
    private String street;
    private String postalCode;
    private String city;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}