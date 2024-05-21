package com.example.userbridge.infrastructure.api;

import com.example.userbridge.domain.user.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationRequest {

    @Valid
    private UserDto userDto;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
