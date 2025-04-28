package com.cts.SmartHotelBookingSystem.service;

import com.cts.SmartHotelBookingSystem.model.Review;
import com.cts.SmartHotelBookingSystem.model.Room;
import com.cts.SmartHotelBookingSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.SmartHotelBookingSystem.repository.ReviewRepository;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public List<Room> getRoomsByHotelIdAndGuests(Long hotelId, int guests) {
        return roomRepository.findAvailableRooms(hotelId, guests);
    }

    public void saveReview(Review review) {
        System.out.println("Content received in saveReview: " + review.getContent()); // Debugging line
    
        if (review.getContent() == null || review.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        reviewRepository.save(review);
    }
}