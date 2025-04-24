package com.cts.SmartHotelBookingSystem.service;

import com.cts.SmartHotelBookingSystem.model.User;
import com.cts.SmartHotelBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // New method to check if username or email is already taken
    public boolean isUsernameOrEmailTaken(String username, String email) {
        return userRepository.findByUsername(username) != null || userRepository.findByEmail(email) != null;
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public boolean validatePassword(User user, String rawPassword) {
        // Assuming passwords are stored as plain text (not recommended in production)
        return user.getPassword().equals(rawPassword);
    }
}
