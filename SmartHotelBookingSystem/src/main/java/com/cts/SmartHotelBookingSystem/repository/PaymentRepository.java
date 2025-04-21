package com.cts.SmartHotelBookingSystem.repository;

import com.cts.SmartHotelBookingSystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Custom query to find payments by booking ID
    Payment findByBookingId(Long bookingId);

    // Custom query to find payments by status
    List<Payment> findByStatus(String status);
}