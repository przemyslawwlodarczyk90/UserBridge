package com.example.userbridge.infrastructure.api;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.facade.UserFacade;
import com.example.userbridge.domain.user.service.ConfirmationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;

    public UserController(UserFacade userFacade, ConfirmationService confirmationService) {
        this.userFacade = userFacade;
        this.confirmationService = confirmationService;
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
    public ResponseEntity<String> editUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        userFacade.editUser(id, userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam String token) {
        confirmationService.confirmToken(token);
        return ResponseEntity.ok("User confirmed successfully");
    }
}
