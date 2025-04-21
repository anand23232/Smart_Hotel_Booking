package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Custom query to find reviews by hotel ID
    List<Review> findByHotelId(Long hotelId);

    // Custom query to find reviews by user ID
    List<Review> findByUserId(Long userId);
}