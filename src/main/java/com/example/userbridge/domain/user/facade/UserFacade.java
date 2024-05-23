package com.example.userbridge.domain.user.facade;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.service.*;
import org.springframework.stereotype.Service;

@Service
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

    public void editUser(Long id, UserDto userDto) {
        editUserService.edit(id, userDto);
    }

    public void deleteUser(Long userId) {
        deleteUserService.delete(userId);
    }
}
