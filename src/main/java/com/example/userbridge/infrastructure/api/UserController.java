package com.example.userbridge.infrastructure.api;

import com.example.userbridge.domain.user.dto.LoginDto;
import com.example.userbridge.domain.user.dto.UserDto;
import com.example.userbridge.domain.user.facade.UserFacade;
import com.example.userbridge.domain.user.service.ConfirmationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@Validated
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;

    public UserController(UserFacade userFacade, ConfirmationService confirmationService) {
        this.userFacade = userFacade;
        this.confirmationService = confirmationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", UserDto.builder().build());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                               BindingResult bindingResult, Model model,
                               @RequestParam String password) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userFacade.registerUser(userDto, password);
        model.addAttribute("message", "User registered successfully. Please check your email for confirmation.");
        return "register_success";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto("", ""));
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            String token = userFacade.loginUser(loginDto);
            model.addAttribute("message", "Login successful");
            return "redirect:/users/profile"; // Replace with the actual profile page
        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        // Assuming you have a method to get the currently logged in user
        UserDto userDto = userFacade.getUserByEmail("test@example.com"); // Replace with actual email fetching logic
        model.addAttribute("userDto", userDto);
        return "user_details";
    }

    @PostMapping("/profile")
    public String editUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user_details";
        }
        userFacade.editUser(userDto.id(), userDto);
        model.addAttribute("message", "Profile updated successfully");
        return "user_details";
    }

    @GetMapping("/confirm")
    public String confirmUser(@RequestParam String token, Model model) {
        try {
            confirmationService.confirmToken(token);
            model.addAttribute("message", "User confirmed successfully");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "login";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDto> users = userFacade.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("*")
    public String fallback() {
        return "redirect:/users/home";
    }
}
