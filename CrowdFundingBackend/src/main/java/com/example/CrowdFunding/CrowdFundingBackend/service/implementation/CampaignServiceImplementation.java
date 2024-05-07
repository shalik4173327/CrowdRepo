package com.example.CrowdFunding.CrowdFundingBackend.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CrowdFunding.CrowdFundingBackend.model.Admin;
import com.example.CrowdFunding.CrowdFundingBackend.model.Campaign;
import com.example.CrowdFunding.CrowdFundingBackend.model.Payment;
import com.example.CrowdFunding.CrowdFundingBackend.repository.CampaignRepository;
import com.example.CrowdFunding.CrowdFundingBackend.service.interfaces.CampaignService;

@Service
public class CampaignServiceImplementation implements CampaignService {
    
    
    @Autowired
    private CampaignRepository campaignRepository;
    
    @Override
    public Campaign saveCampaign(Campaign campaign){
        return campaignRepository.save(campaign);
    }

    @Override
    public Optional<Campaign> findById(final Integer aId){
        return campaignRepository.findById(aId);
    }

    @Override
    public Iterable<Campaign> findAll(){
        return campaignRepository.findAll();
    }

    @Override
    public void deleteById(final Integer aId){
        campaignRepository.deleteById(aId);
    }

    @Override
    public Campaign updateCampaign(Integer id, final Campaign campaign) {
        // Check if campaign exists before updating
        Optional<Campaign> existingCampaignOptional = findById(id);
        if (existingCampaignOptional.isPresent()) {
            Campaign existingCampaign = existingCampaignOptional.get();

            existingCampaign.setTitle(campaign.getTitle());
            existingCampaign.setDescription(campaign.getDescription());
            existingCampaign.setGoalAmount(campaign.getGoalAmount());
            existingCampaign.setStartDate(campaign.getStartDate());
            existingCampaign.setEndDate(campaign.getEndDate());
            existingCampaign.setCategory(campaign.getCategory());
            existingCampaign.setStatus(campaign.getStatus());

            // Save the updated campaign
            return campaignRepository.save(existingCampaign);
        } else {
            // throw exception when id not found
            throw new RuntimeException("Campaign with ID " + campaign.getCampaignId() + " not found for update.");
        }
    }

    //method to find active campaign for the user
    public Iterable<Campaign> findActiveCampaigns(){

        try{
            return campaignRepository.findByStatus("Active");
        } catch(Exception e){
            throw new RuntimeException("Failed to find active campaign: " + e.getMessage());
        }
    }

    public void reflectPaymentInCampaign(Payment payment) {
        try {
            // Retrieve the campaign associated with the payment
            Campaign campaign = payment.getCampaign();

            // Update the campaign's raised amount
            campaign.setRaisedAmount(campaign.getRaisedAmount() + payment.getAmount());

            // Recalculate pending amount
            campaign.setPendingAmount(campaign.getGoalAmount() - campaign.getRaisedAmount());

            // Save the updated campaign
            campaignRepository.save(campaign);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reflect payment in campaign: " + e.getMessage());
        }
    }


    // Method to get all campaigns associated with a specific admin
    public List<Campaign> getAllCampaignsByAdmin(Admin admin) {
        return campaignRepository.findByAdmin(admin);
    }

    // Method to get a specific campaign associated with a specific admin and campaign ID
    public Campaign getCampaignByAdminAndCampaignId(Admin admin, Integer campaignId) {
        return campaignRepository.findByAdminAndCampaignId(admin, campaignId);
    }
    
}
