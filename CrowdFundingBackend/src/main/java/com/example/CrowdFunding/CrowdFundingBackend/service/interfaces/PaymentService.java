package com.example.CrowdFunding.CrowdFundingBackend.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.CrowdFunding.CrowdFundingBackend.model.Payment;

public interface PaymentService {
    
    public List<Payment> getAllPayments() ;

    public Optional<Payment> getPaymentById(Integer id);

    public Payment createPayment(Payment payment);

    

}
