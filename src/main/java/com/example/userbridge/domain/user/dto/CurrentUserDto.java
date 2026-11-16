package com.example.userbridge.domain.user.dto;

import java.util.Set;

/** Profile of the currently logged-in user together with granted roles. */
public record CurrentUserDto(UserDto user, Set<String> roles) {}
