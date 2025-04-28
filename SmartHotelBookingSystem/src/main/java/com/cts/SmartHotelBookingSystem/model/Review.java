package com.cts.SmartHotelBookingSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEWS")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID", nullable = false)
    @JsonIgnore
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "REVIEWER", length = 255)
    private String reviewer;

    @Column(name = "CONTENT", nullable = false, columnDefinition = "CLOB")
    private String content;

    @Column(name = "RATING", nullable = false)
    @Min(1)
    @Max(5)
    private int rating;

    @Column(name = "REPLY", columnDefinition = "CLOB")
    private String reply;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", reviewer='" + reviewer + '\'' +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id != null && id.equals(review.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}