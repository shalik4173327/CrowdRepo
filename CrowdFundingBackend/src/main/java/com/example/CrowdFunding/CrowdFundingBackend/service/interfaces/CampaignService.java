package com.example.CrowdFunding.CrowdFundingBackend.service.interfaces;

import java.util.Optional;

import com.example.CrowdFunding.CrowdFundingBackend.model.Campaign;

public interface CampaignService {
    
    public Campaign saveCampaign(Campaign campaign);

    public Optional<Campaign> findById(final Integer aId);

    public Iterable<Campaign> findAll();

    public void deleteById(final Integer aId);

    public Campaign updateCampaign(Integer id, final Campaign campaign);
    
}
