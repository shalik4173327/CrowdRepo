package com.example.CrowdFunding.CrowdFundingBackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CampaignNotFoundException extends RuntimeException {
    
    public CampaignNotFoundException(String message){
        super(message);
    }
}
