package com.example.CrowdFunding.CrowdFundingBackend.response;

import com.example.CrowdFunding.CrowdFundingBackend.domain.ROLE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    
    private String message;
    private String jwt;
    private ROLE role;
}
