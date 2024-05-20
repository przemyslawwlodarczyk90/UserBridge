package com.example.userbridge.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Builder
@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String street;
    private String postalCode;
    private String city;
}
