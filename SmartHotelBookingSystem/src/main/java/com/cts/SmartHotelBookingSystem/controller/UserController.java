package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
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
    public String loginPage(@RequestParam(value = "success", required = false) String success, 
                            @RequestParam(value = "logout", required = false) String logout, 
                            Model model) {
        if (success != null) {
            model.addAttribute("success", success);
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "users/login"; // Maps to login.html
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, 
                            @RequestParam String password, 
                            HttpSession session, 
                            Model model) {
        User user = userService.findByUsername(username);
        if (user != null && userService.validatePassword(user, password)) {
            // Store the logged-in user in the session
            session.setAttribute("loggedInUser", user);

            // Check the user's role and redirect accordingly
            String role = user.getRole().toLowerCase();
            switch (role) {
                case "admin":
                    return "redirect:/users/admin/dashboard"; // Redirect to admin dashboard
                case "manager":
                    return "redirect:/users/hotel-manager/dashboard"; // Redirect to hotel manager dashboard
                case "customer":
                    return "redirect:/users/users/dashboard"; // Redirect to guest dashboard
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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session to log out the user
        return "redirect:/users/login?logout=true"; // Redirect to the login page with a logout message
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "users/register"; // Maps to register.html in the users folder
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, 
                               @RequestParam(required = false) String hotelName, 
                               RedirectAttributes redirectAttributes) {
        // Check if the username or email is already taken
        if (userService.isUsernameOrEmailTaken(user.getUsername(), user.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Username or Email is already taken. Please choose another.");
            return "redirect:/users/register";
        }

        // If the role is "manager", ensure the hotel name is provided
        if ("manager".equalsIgnoreCase(user.getRole()) && (hotelName == null || hotelName.trim().isEmpty())) {
            redirectAttributes.addFlashAttribute("error", "Hotel name is required for managers.");
            return "redirect:/users/register";
        }

        // Set the hotel name for managers
        if ("manager".equalsIgnoreCase(user.getRole())) {
            user.setHotelName(hotelName);
        }

        // Save the user
        userService.saveUser(user);

        // Add a success message and redirect to the login page
        redirectAttributes.addFlashAttribute("success", "Account created successfully! Please log in.");
        return "redirect:/users/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // Maps to admin/dashboard.html
    }

    @GetMapping("/hotel-manager/dashboard")
    public String managerDashboard() {
        return "hotel-manager/dashboard"; // Maps to hotel-manager/dashboard.html
    }

    @GetMapping("/users/dashboard")
    public String guestDashboard() {
        return "/users/dashboard"; // Maps to guest/dashboard.html
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