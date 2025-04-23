package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users"; // Maps to users.html
    }
@GetMapping("/login")
public String loginPage(Model model) {
    return "users/login"; // Maps to login.html
}
@GetMapping("/register")
public String showRegisterPage() {
    return "users/register"; // This maps to register.html in the users folder
}

@PostMapping("/register")
public String registerUser(@ModelAttribute User user, Model model) {
    if (userService.isUsernameOrEmailTaken(user.getUsername(), user.getEmail())) {
        model.addAttribute("error", "Username or Email is already taken. Please choose another.");
        return "users/register"; // Return to the registration page with an error message
    }
    userService.saveUser(user); // Save the user using the UserService
    return "redirect:/users/login"; // Redirect to the login page after successful registration
}


    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create-user"; // Maps to create-user.html
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}