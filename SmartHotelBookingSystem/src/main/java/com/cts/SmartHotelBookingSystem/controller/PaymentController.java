package com.cts.SmartHotelBookingSystem.controller;
import com.cts.SmartHotelBookingSystem.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.razorpay.RazorpayException;
 
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
 
    @Autowired
    private RazorpayService razorpayService;
 
    @PostMapping("/create-order")
    public String createOrder(@RequestParam int amount , @RequestParam String currency){
 
        try {
            return razorpayService.createOrder(amount, currency, "recepient_100");
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
 
}