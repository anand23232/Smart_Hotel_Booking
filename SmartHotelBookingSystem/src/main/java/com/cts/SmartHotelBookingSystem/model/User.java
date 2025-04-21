package com.cts.SmartHotelBookingSystem.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @Column(unique = true, nullable = false)
    private String username; // New field for login

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;
=======
    private String name;
    private String email;
    private String password;
>>>>>>> e385994a205eb7d0d60297549c4a87b41a394be2

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

<<<<<<< HEAD
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
=======
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
>>>>>>> e385994a205eb7d0d60297549c4a87b41a394be2
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
<<<<<<< HEAD

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
=======
>>>>>>> e385994a205eb7d0d60297549c4a87b41a394be2
}