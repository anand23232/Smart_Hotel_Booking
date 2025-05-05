package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
                            HttpServletResponse response,
                            Model model) {
        System.out.println("Setting cookie for user: " + username);

        User user = userService.findByUsername(username);
        if (user != null && userService.validatePassword(user, password)) {
            // Store the logged-in user in the session
            session.setAttribute("loggedInUser", user);
            System.out.println("User logged in: " + user);

            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setHttpOnly(true); // Set HttpOnly to prevent JavaScript access
            usernameCookie.setPath("/"); // Set the path for the cookie
            usernameCookie.setMaxAge(3600 * 24 * 7); // Set the cookie to expire in 1 day (in seconds)
            response.addCookie(usernameCookie); // Add the cookie to the response

            // Check the user's role and redirect accordingly
            String role = user.getRole().toLowerCase();
            switch (role) {
                case "admin":
                    return "redirect:/admin/dashboard"; // Redirect to admin dashboard (corrected mapping)
                case "manager":
                    return "redirect:/hotel-manager/dashboard"; // Redirect to hotel manager dashboard (corrected mapping)
                case "customer":
                    return "redirect:/users/dashboard"; // Redirect to guest dashboard (corrected mapping)
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
    public String logout(HttpServletResponse response) {
        // Create a new cookie with the same name as the login cookie
        Cookie usernameCookie = new Cookie("username", null);
        // Set the cookie's maximum age to 0 to make it expire immediately
        usernameCookie.setMaxAge(0);
        // Set the path to ensure the browser knows which path the cookie belongs to
        usernameCookie.setPath("/");
        // Add the expired cookie to the response
        response.addCookie(usernameCookie);

        // Optionally, you might want to invalidate the session as well
        // request.getSession().invalidate();

        // Redirect the user to the login page or any other appropriate page
        return "redirect:/users/login"; // Assuming you have a /login page
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

   
    @GetMapping({ "/hotel-manager/dashboard", "/dashboard"})
    public String dashboard(@CookieValue(value = "username", required = false) String username, Model model) {
        if (username != null && !username.isEmpty()) {
            // Retrieve user details from the database using the username from the cookie
            User user = userService.findByUsername(username);
            if (user != null) {
                // User found, add user details to the model for the dashboard
                model.addAttribute("loggedInUser", user);
                // Determine the view based on the user's role
                 if (user.getRole().equals("MANAGER")) {
                    return "hotel-manager/dashboard"; // Maps to hotel-manager/dashboard.html
                } else {
                    return "users/dashboard"; // Maps to users/dashboard.html (for regular guests)
                }
            }
        }
        // If the username cookie is missing or the user is not found, redirect to the login page
        return "redirect:/users/login";
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

    @GetMapping("/profile") // Corrected mapping to be under /users
    public String userProfile(@CookieValue(value = "username", defaultValue = "") String username, Model model) {
        if (!username.isEmpty()) {
            // Fetch the user details using the username
            User user = userService.findByUsername(username);
            if (user != null) {
                // Add the user details to the model
                model.addAttribute("userDetails", user);
                System.out.println("User details: " + user);
            } else {
                model.addAttribute("error", "User not found");
            }
        } else {
            model.addAttribute("error", "Username not found in cookies");
        }

        return "users/profile"; // Maps to users/profile.html
    }
}