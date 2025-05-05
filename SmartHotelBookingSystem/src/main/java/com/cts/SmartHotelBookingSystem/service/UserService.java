package com.cts.SmartHotelBookingSystem.service;

import com.cts.SmartHotelBookingSystem.model.Hotel;
import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.repository.HotelRepository;
import com.cts.SmartHotelBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        // Save the user
        userRepository.save(user);

        // If the user is a manager, update the hotel table
        if ("manager".equalsIgnoreCase(user.getRole()) && user.getHotelName() != null) {
            // Find the hotel by name
            Hotel hotel = hotelRepository.findByName(user.getHotelName());
            if (hotel == null) {
                // Create a new hotel if it doesn't exist
                hotel = new Hotel();
                hotel.setName(user.getHotelName());
                hotel.setLocation("Default Location"); // Set a default location or get it from the user
                hotel.setManager(user);
                hotelRepository.save(hotel);
            } else {
                // Update the existing hotel's manager
                if (!user.equals(hotel.getManager())) { // Avoid unnecessary updates
                    hotel.setManager(user);
                    hotelRepository.save(hotel);
                }
            }
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public boolean isUsernameOrEmailTaken(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean validatePassword(User user, String password) {
        // Add password validation logic here (e.g., hashing)
        return user.getPassword().equals(password);
    }
}
