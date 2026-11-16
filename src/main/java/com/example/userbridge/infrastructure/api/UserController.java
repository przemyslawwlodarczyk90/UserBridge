package com.example.userbridge.infrastructure.api;

import com.example.userbridge.domain.user.dto.CurrentUserDto;
import com.example.userbridge.domain.user.dto.ForgotPasswordDto;
import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.ResetPasswordDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.facade.UserFacade;
import com.example.userbridge.domain.user.mapper.UserDtoMapper;
import com.example.userbridge.domain.user.service.ConfirmationService;
import com.example.userbridge.infrastructure.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Validated
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;
    private final UserDtoMapper userDtoMapper;

    public UserController(UserFacade userFacade, ConfirmationService confirmationService, UserDtoMapper userDtoMapper) {
        this.userFacade = userFacade;
        this.confirmationService = confirmationService;
        this.userDtoMapper = userDtoMapper;
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

    @Operation(summary = "Get the profile of the currently logged-in user")
    @GetMapping("/me")
    public ResponseEntity<CurrentUserDto> me(@AuthenticationPrincipal UserDetailsImpl principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Set<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(new CurrentUserDto(userDtoMapper.toUserDto(principal.getUser()), roles));
    }

    @Operation(summary = "Edit an existing user (owner or admin)")
    @PreAuthorize("@userAccess.isSelfOrAdmin(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        userFacade.editUser(id, userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @Operation(summary = "Delete a user (owner or admin)")
    @PreAuthorize("@userAccess.isSelfOrAdmin(#id)")
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

    @Operation(summary = "Send a password reset link to the given e-mail (always returns 200)")
    @PostMapping("/password/forgot")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDto request) {
        userFacade.requestPasswordReset(request.email());
        return ResponseEntity.ok("If an account with this email exists, a reset link has been sent");
    }

    @Operation(summary = "Set a new password using the token from the reset e-mail")
    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDto request) {
        userFacade.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok("Password has been changed successfully");
    }

    @Operation(summary = "Get a list of all users (admin only)")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userFacade.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
