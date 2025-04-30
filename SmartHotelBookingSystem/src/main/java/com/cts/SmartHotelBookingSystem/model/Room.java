package com.cts.SmartHotelBookingSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ROOMNUMBER", nullable = false) // Match the database column name
    private String roomNumber;

    @Column(name = "TYPE", nullable = false) // Match the database column name
    private String type;

    @Column(name = "PRICE", nullable = false) // Match the database column name
    private Double price;

    @Column(name = "CAPACITY", nullable = false) // Match the database column name
    private Integer capacity;

    @Column(name = "AVAILABLEROOMS", nullable = false) // Match the database column name
    private Integer availableRooms;

    @Column(name = "IMAGE_URL", nullable = false) // Match the database column name
    private String imageUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "HOTEL_ID", nullable = false) // Match the database column name
    private Hotel hotel;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(Integer availableRooms) {
        this.availableRooms = availableRooms;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", capacity=" + capacity +
                ", availableRooms=" + availableRooms +
                ", imageUrl='" + imageUrl + '\'' +
                ", hotel=" + hotel +
                '}';
    }
}