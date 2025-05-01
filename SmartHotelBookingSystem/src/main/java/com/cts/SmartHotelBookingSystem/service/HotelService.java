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
<<<<<<< HEAD
        List<Hotel> hotels = hotelRepository.findAll();
        System.out.println("Hotels fetched: " + hotels.size()); // Debug log
        return hotels;
=======
        return hotelRepository.findAll(); // Retrieve all hotels
>>>>>>> e7fedcd166ffb66bd7d2b5f34b5e5a8b685b3077
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