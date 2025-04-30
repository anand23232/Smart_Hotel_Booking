package com.cts.SmartHotelBookingSystem.controller;

import com.cts.SmartHotelBookingSystem.model.Booking;
import com.cts.SmartHotelBookingSystem.service.BookingService;
import com.cts.SmartHotelBookingSystem.service.RoomService;
import com.cts.SmartHotelBookingSystem.service.UserService;
import com.cts.SmartHotelBookingSystem.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookings"; // Maps to bookings.html
    }

    @GetMapping("/create")
    public String createBookingForm(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("booking", new Booking());
        return "create-booking"; // Maps to create-booking.html
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute Booking booking) {
        bookingService.saveBooking(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings";
    }

    @GetMapping("/{roomId}")
    public String getBookingPage(@PathVariable Long roomId, Model model) {
        // Fetch the room details by roomId
        Room room = roomService.getRoomById(roomId);

        // Add the room details to the model
        model.addAttribute("room", room);

        // Return the booking.html page
        return "booking"; // Maps to booking.html
    }
}