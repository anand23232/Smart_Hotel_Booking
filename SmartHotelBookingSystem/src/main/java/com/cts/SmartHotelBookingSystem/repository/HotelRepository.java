package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cts.SmartHotelBookingSystem.model.User; // Add this import for the User class

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByLocation(String location);

    // Custom query to find hotels by name (case-insensitive)
    List<Hotel> findByNameIgnoreCaseContaining(String name);

    // Custom query to search hotels by name or location (case-insensitive)
    List<Hotel> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location);

    Hotel findByName(String name);
    Hotel findByManager(User manager);
}