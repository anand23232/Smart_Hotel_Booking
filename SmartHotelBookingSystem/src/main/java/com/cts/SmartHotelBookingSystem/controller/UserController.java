package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String loginPage(@RequestParam(value = "success", required = false) String success, Model model) {
        if (success != null) {
            model.addAttribute("success", success);
        }
        return "users/login"; // Maps to login.html
    }

    @PostMapping("/login")
public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
    User user = userService.findByUsername(username);
    if (user != null && userService.validatePassword(user, password)) {
        // Check the user's role and redirect accordingly
        String role = user.getRole().toLowerCase();
        switch (role) {
            case "admin":
                return "redirect:/users/admin/dashboard"; // Redirect to admin dashboard
            case "manager":
                return "redirect:/users/hotel-manager/dashboard"; // Redirect to hotel manager dashboard
            case "customer":
                return "redirect:/users/guest/dashboard"; // Redirect to guest dashboard
            default:
                model.addAttribute("error", "Invalid role assigned to the user.");
                return "users/login"; // Redirect back to login with an error
        }
    } else {
        // Login failed, show error message
        model.addAttribute("error", "Invalid username or password");
        return "users/login";
    }
}


    @GetMapping("/register")
    public String showRegisterPage() {
        return "users/register"; // Maps to register.html in the users folder
    }

    @PostMapping("/register")
public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
    if (userService.isUsernameOrEmailTaken(user.getUsername(), user.getEmail())) {
        redirectAttributes.addFlashAttribute("error", "Username or Email is already taken. Please choose another.");
        return "redirect:/users/register"; // Redirect back to the registration page with an error
    }
    userService.saveUser(user); // Save the user using the UserService
    redirectAttributes.addFlashAttribute("success", "Account created successfully! Please log in.");
    return "redirect:/users/login"; // Redirect to the login page after successful registration
}

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // Maps to admin/dashboard.html
    }

    @GetMapping("/hotel-manager/dashboard")
    public String managerDashboard() {
        return "hotel-manager/dashboard"; // Maps to hotel-manager/dashboard.html
    }

    @GetMapping("/guest/dashboard")
    public String guestDashboard() {
        return "guest/dashboard"; // Maps to guest/dashboard.html
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