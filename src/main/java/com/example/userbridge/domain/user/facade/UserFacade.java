package com.example.userbridge.domain.user.facade;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.service.DeleteUserService;
import com.example.userbridge.domain.user.service.EditUserService;
import com.example.userbridge.domain.user.service.LoginService;
import com.example.userbridge.domain.user.service.RegistrationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserFacade {
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final EditUserService editUserService;
    private final DeleteUserService deleteUserService;

    public UserFacade(RegistrationService registrationService, LoginService loginService,
                      EditUserService editUserService, DeleteUserService deleteUserService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
        this.editUserService = editUserService;
        this.deleteUserService = deleteUserService;
    }

    public void registerUser(UserDto userDto, String password) {
        registrationService.register(userDto, password);
    }

    public String loginUser(LoginDto loginDto) {
        return loginService.login(loginDto);
    }

    public void editUser(UserDto userDto) {
        editUserService.edit(userDto);
    }

    public void deleteUser(UUID userId) {
        deleteUserService.delete(userId);
    }
}
