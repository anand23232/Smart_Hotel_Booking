package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find a user by email
    User findByEmail(String email);

    // Custom query to find a user by username
    User findByUsername(String username);

    // Custom query to find users by name (case-insensitive)
    List<User> findByNameIgnoreCaseContaining(String name);

    // Custom query to find users by role
    List<User> findByRole(String role);

    // Custom query to find managers by hotel name
    List<User> findByHotelNameIgnoreCase(String hotelName);

    // Custom query to check if a username or email is already taken
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Custom query to check if either username or email exists
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username OR u.email = :email")
    boolean existsByUsernameOrEmail(String username, String email);
}