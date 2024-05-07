package com.example.CrowdFunding.CrowdFundingBackend.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.CrowdFunding.CrowdFundingBackend.model.Donor;

public interface DonorService {
    
    public List<Donor> getAllDonors();

    public Optional<Donor> getDonorById(int userId);

    public Donor createDonor(Donor donor);
    
    public Donor updateDonor(int donorId, Donor newDonor);

    public void deleteDonor(int donorId);

    
}
