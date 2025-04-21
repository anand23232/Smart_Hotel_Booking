package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Custom query to find rooms by hotel ID
    List<Room> findByHotelId(Long hotelId);

    // Custom query to find rooms by type
    List<Room> findByType(String type);

    // Custom query to find rooms by price range
    List<Room> findByPriceBetween(double minPrice, double maxPrice);
}