package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.model.Room;
import com.cts.SmartHotelBookingSystem.service.RoomService;
import com.cts.SmartHotelBookingSystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addRoom(@ModelAttribute Room room) {
        if (room.getHotel() == null || room.getHotel().getId() == null) {
            return ResponseEntity.badRequest().body("Hotel ID is required to add a room.");
        }

        roomService.saveRoom(room); // Save the room to the database
        return ResponseEntity.ok("Room added successfully!");
    }

    @GetMapping
    public String viewRooms(@RequestParam(required = false) Long hotelId, Model model) {
        if (hotelId == null) {
            model.addAttribute("error", "Hotel ID is required to view rooms.");
            return "error"; // Render an error page if hotelId is missing
        }

        // Fetch rooms for the specified hotel
        List<Room> rooms = roomService.getRoomsByHotelId(hotelId);
        model.addAttribute("rooms", rooms);

        // Fetch the hotel details and add to the model
        Hotel hotel = hotelService.getHotelById(hotelId);
        model.addAttribute("hotel", hotel);

        return "rooms"; // Render rooms.html
    }

    @GetMapping("/create")
    public String createRoomForm(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels(); // Fetch all hotels
        model.addAttribute("hotels", hotels); // Add hotels to the model
        model.addAttribute("room", new Room()); // Add an empty Room object to the model
        return "create-room"; // Ensure this matches the name of your Thymeleaf template
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
            @RequestParam(required = false) Long hotelId,
            @RequestParam(required = false) String checkIn,
            @RequestParam(required = false) String checkOut,
            @RequestParam(required = false, defaultValue = "1") int guests,
            @RequestParam(required = false, defaultValue = "1") int rooms,
            Model model) {

        // Validate required parameters
        if (hotelId == null || checkIn == null || checkOut == null) {
            model.addAttribute("error", "Please provide all required parameters.");
            return "error"; // Render an error page if parameters are missing
        }

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