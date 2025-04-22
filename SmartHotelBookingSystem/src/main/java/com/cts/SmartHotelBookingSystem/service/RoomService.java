package com.cts.SmartHotelBookingSystem.service;

import com.cts.SmartHotelBookingSystem.model.Room;
import com.cts.SmartHotelBookingSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Fetch all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Fetch available rooms based on query parameters
    public List<Room> getAvailableRooms(String checkIn, String checkOut, int guests, int rooms) {
        // Replace this logic with your actual database query or filtering logic
        List<Room> allRooms = getAllRooms();
        return allRooms.stream()
                .filter(room -> room.getCapacity() >= guests && room.getAvailableRooms() >= rooms)
                .collect(Collectors.toList());
    }

    // Save a room
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    // Delete a room
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}