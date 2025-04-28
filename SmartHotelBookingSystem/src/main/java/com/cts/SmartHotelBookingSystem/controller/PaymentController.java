package com.cts.SmartHotelBookingSystem.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.SmartHotelBookingSystem.model.Payment;
import com.cts.SmartHotelBookingSystem.service.PaymentService;
 
@Controller
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
 
    @GetMapping
    public String getAllPayments(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payments"; // Maps to payments.html
    }
 
    @GetMapping("/create")
    public String createPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        return "create-payment"; // Maps to create-payment.html
    }
 
    @PostMapping("/create")
    public String createPayment(@ModelAttribute Payment payment) {
        paymentService.savePayment(payment);
        return "redirect:/payments";
    }
 
    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "redirect:/payments";
    }
}