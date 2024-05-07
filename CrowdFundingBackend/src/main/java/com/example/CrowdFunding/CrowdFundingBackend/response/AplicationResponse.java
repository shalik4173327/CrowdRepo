package com.example.CrowdFunding.CrowdFundingBackend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AplicationResponse {
    
    private String message;
    private boolean status;
}
