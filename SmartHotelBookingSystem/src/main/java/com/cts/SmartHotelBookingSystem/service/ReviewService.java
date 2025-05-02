package com.cts.SmartHotelBookingSystem.service;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.model.Review;
import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.repository.HotelRepository;
import com.cts.SmartHotelBookingSystem.repository.ReviewRepository;
import com.cts.SmartHotelBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    // Fetch all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Fetch a review by its ID
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    // Fetch reviews by user ID
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    // Fetch reviews by hotel ID
    public List<Review> getReviewsByHotelId(Long hotelId) {
        return reviewRepository.findByHotelId(hotelId);
    }

    // Check if a user has already reviewed a specific hotel
    public boolean hasUserReviewedHotel(Long userId, Long hotelId) {
        return reviewRepository.existsByUserIdAndHotelId(userId, hotelId);
    }

    // Save a review
    public Review saveReview(Long userId, Long hotelId, String content, int rating) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Fetch the hotel
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelId));

        // Validate the rating
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // Validate the content
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }

        // Create and save the review
        Review review = new Review();
        review.setUser(user); // Automatically sets the reviewer name
        review.setHotel(hotel);
        review.setContent(content);
        review.setRating(rating);

        return reviewRepository.save(review);
    }

    // Delete a review by its ID
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}