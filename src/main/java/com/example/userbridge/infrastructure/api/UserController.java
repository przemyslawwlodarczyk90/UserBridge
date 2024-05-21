package com.example.userbridge.infrastructure.api;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.facade.UserFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        userFacade.registerUser(request.getUserDto(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDto loginDto) {
        String token = userFacade.loginUser(loginDto);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable UUID id, @Valid @RequestBody UserDto userDto) {
        userDto.setId(id);
        userFacade.editUser(userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userFacade.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
