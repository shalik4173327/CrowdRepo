package com.example.CrowdFunding.CrowdFundingBackend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CrowdFunding.CrowdFundingBackend.model.Campaign;
import com.example.CrowdFunding.CrowdFundingBackend.service.implementation.CampaignServiceImplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    
    @Autowired
    private CampaignServiceImplementation campaignServiceImplementation;

    //swagger documentation
	@Operation(summary = "Create campaign", description = "This is the api endpoint for creating a campaign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    // Creating campaign
    @PostMapping
    public ResponseEntity<Campaign> saveCampaign(@RequestBody Campaign campaign) {
        try {
            Campaign savedCampaign = campaignServiceImplementation.saveCampaign(campaign);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCampaign);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //swagger documentation
	@Operation(summary = "Get campaign details", description = "This is the api endpoint for retirving all the camapgin details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    // Getting details of all the campaign
    @GetMapping
    public ResponseEntity<Iterable<Campaign>> getAllCampaigns() {
        try {
            Iterable<Campaign> campaigns = campaignServiceImplementation.findAll();
            return ResponseEntity.ok(campaigns);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //swagger documentation
	@Operation(summary = "Filter campaign by id", description = "This is the api endpoint for filtering campaign by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    // Getting details of campaign by the id
    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable("campaignId") Integer id) {
        try {
            Optional<Campaign> campaign = campaignServiceImplementation.findById(id);
            return campaign.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //swagger documentation
	@Operation(summary = "Campaign update", description = "This is the api endpoint for updating the campaign by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })

    // Updating the campaign by id
    @PutMapping("/{campaignId}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable("campaignId") Integer id, @RequestBody Campaign campaignDetails) {
        try {
            Campaign updatedCampaign = campaignServiceImplementation.updateCampaign(id, campaignDetails);
            return ResponseEntity.ok(updatedCampaign);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //swagger documentation
	@Operation(summary = "Delete Campaign", description = "This is the api endpoint for removing the campaign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    // Deleteing the campaign by id
    @DeleteMapping("/{campaignId}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable("campaignId") Integer id) {
        try {
            campaignServiceImplementation.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //swagger documentation
	@Operation(summary = "Get active Campaign", description = "This is the api endpoint for getting the  whose status is active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    @GetMapping("/activeCampaigns")
    // Viewing the active campaign by the user
    public ResponseEntity<Iterable<Campaign>> getActiveCampaigns() {
        try {
            Iterable<Campaign> activeCampaigns = campaignServiceImplementation.findActiveCampaigns();
            return ResponseEntity.ok(activeCampaigns);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
