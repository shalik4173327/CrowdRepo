package com.example.CrowdFunding.CrowdFundingBackend.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CrowdFunding.CrowdFundingBackend.model.Campaign;
import com.example.CrowdFunding.CrowdFundingBackend.model.Donor;
import com.example.CrowdFunding.CrowdFundingBackend.model.Payment;
import com.example.CrowdFunding.CrowdFundingBackend.repository.CampaignRepository;
import com.example.CrowdFunding.CrowdFundingBackend.repository.DonorRepository;
import com.example.CrowdFunding.CrowdFundingBackend.repository.PaymenRepository;
import com.example.CrowdFunding.CrowdFundingBackend.service.interfaces.PaymentService;

@Service
public class PaymentServiceImplementation implements PaymentService{
    
    @Autowired
    private PaymenRepository paymentRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired 
    private DonorRepository donorRepository;

    @Override
    // to get all the details
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    //to get details of specific payment made
    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    @Override
    // when user is making a payment
    public Payment createPayment(Payment payment) {
        try {
            // Fetch user by email
            Optional<Donor> donorOptional = donorRepository.findByEmail(payment.getDonor().getEmail());
            Donor donor = donorOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Fetch campaign by title
            Optional<Campaign> campaignOptional = campaignRepository.findByTitle(payment.getCampaign().getTitle());
            Campaign campaign = campaignOptional.orElseThrow(() -> new IllegalArgumentException("Campaign not found"));

            // Set the user and campaign in the payment
            payment.setDonor(donor);
            payment.setCampaign(campaign);

            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save payment: " + e.getMessage());
        }
    }

}
