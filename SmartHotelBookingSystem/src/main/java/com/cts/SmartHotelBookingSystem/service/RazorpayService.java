package com.cts.SmartHotelBookingSystem.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class RazorpayService {

    @Value("${razorpay.api.key}")
    private String apiKey ;

    @Value("${razorpay.api.secret}")
    private String apiSecret ;

    public String createOrder(int amount , String currency , String receiptId) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);
        JSONObject orderRequest  = new JSONObject();

        if ("INR".equalsIgnoreCase(currency)) {
            orderRequest.put("amount", amount * 100); // Convert to paise for INR
        } else if ("USD".equalsIgnoreCase(currency)) {
            orderRequest.put("amount", amount);       // Amount is already in cents for USD
        } else {
            // Handle other currencies if needed, or throw an error for unsupported currencies
            throw new IllegalArgumentException("Currency not supported: " + currency);
        }

        orderRequest.put("currency",currency);
        orderRequest.put("receipt", receiptId);

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }
}