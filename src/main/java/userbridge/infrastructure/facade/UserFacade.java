package userbridge.infrastructure.facade;

import org.springframework.stereotype.Component;
import userbridge.domain.user.dto.UserDto;
import userbridge.domain.user.dto.LoginDto;
import userbridge.domain.user.service.DeleteUserService;
import userbridge.domain.user.service.EditUserService;

import userbridge.domain.user.service.LoginService;
import userbridge.domain.user.service.RegistrationService;

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

    public void registerUser(UserDto userDto) {
        registrationService.register(userDto);
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