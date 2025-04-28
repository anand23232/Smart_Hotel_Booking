package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.model.Review;
import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.service.HotelService;
import com.cts.SmartHotelBookingSystem.service.ReviewService;
import com.cts.SmartHotelBookingSystem.service.RoomService; // Import RoomService
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RoomService roomService; // Inject RoomService

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

    // Search hotels by keyword
    @GetMapping("/search")
    @ResponseBody
    public List<Hotel> searchHotels(@RequestParam("keyword") String keyword) {
        return hotelService.searchHotels(keyword);
    }

    // Submit a review for a hotel
    @PostMapping("/submitReview")
    @ResponseBody
    public String submitReview(@RequestParam Long hotelId,
                               @RequestParam String content,
                               @RequestParam int rating,
                               HttpSession session) {
        // Retrieve the logged-in user from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "User not logged in. Please log in to submit a review.";
        }

        if (content == null || content.trim().isEmpty()) {
            return "Comment cannot be empty.";
        }

        System.out.println("Content received in submitReview: " + content); // Debugging line

        // Create and save the review
        Review newReview = new Review();
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) {
            return "Hotel not found with id: " + hotelId;
        }
        newReview.setHotel(hotel);
        newReview.setUser(loggedInUser);
        newReview.setContent(content);
        newReview.setRating(rating);

        System.out.println("Review object before saving: " + newReview); // Debugging line

        roomService.saveReview(newReview); // Use RoomService to save the review

        return "Review submitted successfully!";
    }
}