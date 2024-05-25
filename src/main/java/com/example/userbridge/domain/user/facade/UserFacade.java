package com.example.userbridge.domain.user.facade;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.entity.User;
import com.example.userbridge.domain.user.exception.UserNotFoundException;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.domain.user.service.*;
import com.example.userbridge.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFacade {
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final EditUserService editUserService;
    private final DeleteUserService deleteUserService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserFacade(RegistrationService registrationService, LoginService loginService,
                      EditUserService editUserService, DeleteUserService deleteUserService,
                      UserService userService, UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.registrationService = registrationService;
        this.loginService = loginService;
        this.editUserService = editUserService;
        this.deleteUserService = deleteUserService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
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

    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userDtoMapper.toUserDto(user);
    }
}
