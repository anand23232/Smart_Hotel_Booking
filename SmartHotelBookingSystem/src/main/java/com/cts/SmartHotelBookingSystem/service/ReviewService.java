package com.cts.SmartHotelBookingSystem.service;

import com.cts.SmartHotelBookingSystem.model.Review;
import com.cts.SmartHotelBookingSystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    // Fetch all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Fetch a review by its ID
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    // Fetch reviews by hotel ID
    // Removed duplicate method definition

    // Fetch reviews by user ID
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    // Check if a user has already reviewed a specific hotel
    public boolean hasUserReviewedHotel(Long userId, Long hotelId) {
        return reviewRepository.existsByUserIdAndHotelId(userId, hotelId);
    }

    // Save a review
    public Review saveReview(Review review) {
        if (review.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (review.getHotel() == null) {
            throw new IllegalArgumentException("Hotel cannot be null");
        }
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (review.getContent() == null || review.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        return reviewRepository.save(review);
    }

    // Delete a review by its ID
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
    

    public List<Review> getReviewsByHotelId(Long hotelId) {
            return reviewRepository.findByHotelId(hotelId);
    }
}