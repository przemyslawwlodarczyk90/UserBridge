package com.example.userbridge.infrastructure.api;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.facade.UserFacade;
import com.example.userbridge.domain.user.service.ConfirmationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;

    public UserController(UserFacade userFacade, ConfirmationService confirmationService) {
        this.userFacade = userFacade;
        this.confirmationService = confirmationService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        userFacade.registerUser(request.getUserDto(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDto loginDto) {
        String token = userFacade.loginUser(loginDto);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Edit an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        userFacade.editUser(id, userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Confirm a user's registration")
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam String token) {
        confirmationService.confirmToken(token);
        return ResponseEntity.ok("User confirmed successfully");
    }

    @Operation(summary = "Get a list of all users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userFacade.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
