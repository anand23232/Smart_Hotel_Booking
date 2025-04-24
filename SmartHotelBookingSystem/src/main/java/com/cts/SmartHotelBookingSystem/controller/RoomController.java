package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Hotel;
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

    @GetMapping
    public String getAllRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
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

@GetMapping("/search")
public String getRooms(
        @RequestParam Long hotelId,
        @RequestParam String checkIn,
        @RequestParam String checkOut,
        @RequestParam int guests,
        @RequestParam int rooms,
        Model model) {

    // Fetch rooms for the specified hotel
    List<Room> availableRooms = roomService.getRoomsByHotelIdAndGuests(hotelId, guests);

    // Add data to the model
    model.addAttribute("rooms", availableRooms);
    model.addAttribute("checkIn", checkIn);
    model.addAttribute("checkOut", checkOut);
    model.addAttribute("guests", guests);
    model.addAttribute("rooms", rooms);

    // Fetch the hotel details and add to the model
    Hotel hotel = hotelService.getHotelById(hotelId);
    model.addAttribute("hotel", hotel);

    return "rooms"; // Render rooms.html
}
}