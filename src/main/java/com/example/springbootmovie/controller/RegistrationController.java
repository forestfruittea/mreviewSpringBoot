package com.example.springbootmovie.controller;

import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.model.role.Role;
import com.example.springbootmovie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
//        model.addAttribute("roles", Role.values()); // Pass roles to JSP
        log.info("register page was achieved");
        return "register"; // This should map to register.jsp

    }
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        if (userService.findByUsername(username).isPresent()) {
            return "redirect:/register?error";
        }
        String hashedPassword = passwordEncoder.encode(password);
        UserDto newUser = new UserDto();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setRole(Role.ROLE_CUSTOMER);

        userService.registerUser(newUser);
        return "redirect:/register";
    }
}
