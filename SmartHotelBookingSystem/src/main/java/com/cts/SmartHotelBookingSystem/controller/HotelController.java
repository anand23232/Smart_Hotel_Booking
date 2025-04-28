package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cts.SmartHotelBookingSystem.model.Review; // Import the Review class
import com.cts.SmartHotelBookingSystem.service.ReviewService; // Import the ReviewService class

import com.cts.SmartHotelBookingSystem.model.User;


import jakarta.servlet.http.HttpSession;


import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Fetch and display all hotels
    @GetMapping
    public String getAllHotels(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels", hotels);
        return "hotels"; // Maps to hotels.html
    }

    // Render form to create a new hotel
    @GetMapping("/create")
    public String createHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "create-hotel"; // Maps to create-hotel.html
    }

    // Save a new hotel to the database
    @PostMapping("/create")
    public String createHotel(@ModelAttribute Hotel hotel) {
        hotelService.saveHotel(hotel);
        return "redirect:/hotels";
    }

    // Delete a hotel by ID
    @GetMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return "redirect:/hotels";
    }

    // Fetch and display details of a specific hotel
    @GetMapping("/{id}")
    public String getHotelDetails(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id); // Fetch hotel details from the database
        if (hotel == null) {
            throw new RuntimeException("Hotel not found with id: " + id); // Handle invalid ID
        }
        model.addAttribute("hotel", hotel);
        return "hoteldetails"; // Render hoteldetails.html
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Hotel> searchHotels(@RequestParam("keyword") String keyword) {
        return hotelService.searchHotels(keyword);
    }
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submitReview")
    @ResponseBody
    public String submitReview(@RequestParam Long hotelId,
                               @RequestParam String review,
                               @RequestParam int rating,
                               HttpSession session) {
        // Retrieve the logged-in user from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");
    
        if (loggedInUser == null) {
            // If no user is logged in, return an error message
            return "User not logged in. Please log in to submit a review.";
        }
    
        // Create and save the review
        Review newReview = new Review();
        Hotel hotel = hotelService.getHotelById(hotelId); // Fetch the Hotel object using hotelId
        if (hotel == null) {
            return "Hotel not found with id: " + hotelId; // Handle invalid hotelId
        }
        newReview.setHotel(hotel); // Set the fetched Hotel object
        newReview.setUser(loggedInUser); // Use the logged-in user's details
        newReview.setComment(review);
        newReview.setRating(rating);
    
        reviewService.saveReview(newReview);
    
        // Return a success message
        return "Review submitted successfully!";
                               }}