package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByLocation(String location);

<<<<<<< HEAD
    // Custom query to find hotels by name (case-insensitive)
    List<Hotel> findByNameIgnoreCaseContaining(String name);

    // Custom query to search hotels by name or location (case-insensitive)
=======
>>>>>>> e7fedcd166ffb66bd7d2b5f34b5e5a8b685b3077
    List<Hotel> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location);
}