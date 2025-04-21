package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Review;
import com.cts.SmartHotelBookingSystem.service.ReviewService;
import com.cts.SmartHotelBookingSystem.service.HotelService;
import com.cts.SmartHotelBookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "reviews"; // Maps to reviews.html
    }

    @GetMapping("/create")
    public String createReviewForm(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("review", new Review());
        return "create-review"; // Maps to create-review.html
    }

    @PostMapping("/create")
    public String createReview(@ModelAttribute Review review) {
        reviewService.saveReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}