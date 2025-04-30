package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private HotelService hotelService;

    // Method to display the admin dashboard
    
    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels", hotels);
        return "admin/dashboard";
    }

    // Method to handle adding a new hotel
    @PostMapping("/hotels/add")
    public String addHotel(Hotel hotel) {
        hotelService.saveHotel(hotel); // Save the hotel to the database
        return "redirect:/admin/dashboard"; // Redirect back to the dashboard
    }

    @PostMapping("/hotels/edit")
    public String editHotel(Hotel hotel) {
        hotelService.saveHotel(hotel); // Save the updated hotel details
        return "redirect:/admin/dashboard"; // Redirect back to the dashboard
}

@PostMapping("/hotels/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id); // Delete the hotel by ID
        return "redirect:/admin/dashboard"; // Redirect back to the dashboard
    }
// @GetMapping("/hotels/delete/{id}")
// public String deleteHotel(@PathVariable Long id) {
//     hotelService.deleteHotel(id); // Delete the hotel by ID
//     return "redirect:/admin/dashboard"; // Redirect back to the dashboard
// }
}