package com.example.CrowdFunding.CrowdFundingBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CrowdFunding.CrowdFundingBackend.model.Payment;

@Repository
public interface PaymenRepository extends JpaRepository<Payment, Integer> {
    
}
