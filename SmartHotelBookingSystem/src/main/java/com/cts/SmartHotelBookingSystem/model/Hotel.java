package com.cts.SmartHotelBookingSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "HOTEL")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "LOCATION", nullable = false)
    private String location;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "PRICE")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "id", nullable = true)
    private User manager;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}