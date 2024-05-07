package com.example.CrowdFunding.CrowdFundingBackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CrowdFunding.CrowdFundingBackend.model.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer> {
    
    public Optional<Donor> findByEmail(String email);
    
}
