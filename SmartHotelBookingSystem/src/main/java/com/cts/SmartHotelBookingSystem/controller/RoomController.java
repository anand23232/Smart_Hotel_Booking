package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Room;
import com.cts.SmartHotelBookingSystem.service.RoomService;
import com.cts.SmartHotelBookingSystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    // Updated method to handle query parameters for filtering rooms
    @GetMapping
    public String getRooms(
            @RequestParam(value = "checkIn", required = false) String checkIn,
            @RequestParam(value = "checkOut", required = false) String checkOut,
            @RequestParam(value = "guests", required = false, defaultValue = "1") int guests,
            @RequestParam(value = "rooms", required = false, defaultValue = "1") int rooms,
            Model model) {

        // If query parameters are provided, filter rooms based on the parameters
        if (checkIn != null && checkOut != null) {
            List<Room> availableRooms = roomService.getAvailableRooms(checkIn, checkOut, guests, rooms);
            model.addAttribute("rooms", availableRooms);
            model.addAttribute("checkIn", checkIn);
            model.addAttribute("checkOut", checkOut);
            model.addAttribute("guests", guests);
            model.addAttribute("rooms", rooms);
        } else {
            // If no query parameters, display all rooms
            List<Room> roomsList = roomService.getAllRooms();
            model.addAttribute("rooms", roomsList);
        }

        return "rooms"; // Maps to rooms.html
    }

    @GetMapping("/create")
    public String createRoomForm(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        model.addAttribute("room", new Room());
        return "create-room"; // Maps to create-room.html
    }

    @PostMapping("/create")
    public String createRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms";
    }
}