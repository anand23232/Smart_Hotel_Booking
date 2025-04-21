package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query to find bookings by user ID
    List<Booking> findByUserId(Long userId);

    // Custom query to find bookings by room ID
    List<Booking> findByRoomId(Long roomId);
}