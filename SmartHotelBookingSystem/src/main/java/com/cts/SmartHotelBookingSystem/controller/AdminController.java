package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CookieValue;
import com.cts.SmartHotelBookingSystem.service.UserService;
import com.cts.SmartHotelBookingSystem.model.User;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private HotelService hotelService;

   
    @Autowired
    private UserService userService; // Assuming you have a UserService

    @GetMapping("/admin/dashboard")
    public String showDashboard(@CookieValue(value = "username", required = false) String username, Model model) {

        
        if (username != null && !username.isEmpty()) {
           User user = userService.findByUsername(username);

        //    System.out.println("admin foundd..."+user); 
            // if (user != null && user.getRole().equals("ADMIN")) {
                List<Hotel> hotels = hotelService.getAllHotels();
                model.addAttribute("hotels", hotels);
                model.addAttribute("loggedInUser", user); // Optionally add user info to the model
                return "admin/dashboard";
            // }
        }
        return "redirect:/users/login";
    }

    // Method to handle adding a new hotel
    @PostMapping("/hotels/add")
    public String addHotel(Hotel hotel) {
        hotelService.saveHotel(hotel); // Save the hotel to the database
        return "redirect:/admin/dashboard"; // Redirect back to the dashboard
    }

    @GetMapping("admin/adminprofile") // Corrected mapping to be under /users
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

        return "admin/adminprofile"; // Maps to users/profile.html
    }
    

@PostMapping("/hotels/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id); // Delete the hotel by ID
        return "redirect:/admin/dashboard"; // Redirect back to the dashboard
    }

}