package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Custom query to find hotels by location
    List<Hotel> findByLocation(String location);

    // Custom query to find hotels by name (case-insensitive)
    List<Hotel> findByNameIgnoreCaseContaining(String name);
}