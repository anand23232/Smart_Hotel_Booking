package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Fetch all reviews for a specific hotel by its ID
    List<Review> findByHotelId(Long hotelId);

    // Fetch all reviews for a specific user
    List<Review> findByUserId(Long userId);

    // Check if a review exists for a specific user and hotel
    boolean existsByUserIdAndHotelId(Long userId, Long hotelId);

    // Fetch all reviews for a specific hotel sorted by creation date (descending)
    List<Review> findByHotelIdOrderByCreatedAtDesc(Long hotelId);

    // Fetch all reviews for a specific hotel entity
    List<Review> findByHotel(Hotel hotel);
}