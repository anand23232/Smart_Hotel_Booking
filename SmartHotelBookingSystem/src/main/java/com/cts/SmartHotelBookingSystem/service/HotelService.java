package com.cts.SmartHotelBookingSystem.service;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    // Fetch all hotels
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll(); // Retrieve all hotels
    }

    // Fetch a hotel by its ID
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    // Fetch hotels by location
    public List<Hotel> getHotelsByLocation(String location) {
        return hotelRepository.findByLocation(location);
    }

    // Save or update a hotel
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel); // Save the hotel to the database
    }

    // Delete a hotel by its ID
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    // Search hotels by name or location
    public List<Hotel> searchHotels(String keyword) {
        return hotelRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword);
    }
}