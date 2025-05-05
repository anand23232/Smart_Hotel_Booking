package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.model.Review;
import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.repository.HotelRepository;
import com.cts.SmartHotelBookingSystem.repository.ReviewRepository;
import com.cts.SmartHotelBookingSystem.service.ReviewService;
import com.cts.SmartHotelBookingSystem.service.HotelService;
import com.cts.SmartHotelBookingSystem.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HotelRepository hotelRepository;

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
        // Extract the required fields from the Review object
        Long userId = review.getUser().getId();
        Long hotelId = review.getHotel().getId();
        String content = review.getContent();
        int rating = review.getRating();

        // Call the updated saveReview method
        reviewService.saveReview(userId, hotelId, content, rating);

        return "redirect:/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }

    // Fetch reviews for the manager's hotel
    @GetMapping("/manager")
    public ResponseEntity<List<Review>> getManagerHotelReviews(Principal principal) {
        String username = principal.getName();
        User manager = userService.findByUsername(username);

        if (manager == null || !"manager".equalsIgnoreCase(manager.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Hotel hotel = hotelRepository.findByManager(manager);
        if (hotel == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Fetch reviews for the manager's hotel
        List<Review> reviews = reviewRepository.findByHotel(hotel);
        return ResponseEntity.ok(reviews);
    }

    // Reply to a review
    @PostMapping("/reply")
    public ResponseEntity<String> replyToReview(@RequestParam Long reviewId, @RequestParam String replyContent, Principal principal) {
        String username = principal.getName();
        User manager = userService.findByUsername(username);

        if (manager == null || !"manager".equalsIgnoreCase(manager.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to reply to this review.");
        }

        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null || !review.getHotel().getManager().equals(manager)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to reply to this review.");
        }

        // Save the reply
        review.setReply(replyContent);
        reviewRepository.save(review);

        return ResponseEntity.ok("Reply added successfully.");
    }
}

