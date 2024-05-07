package com.example.CrowdFunding.CrowdFundingBackend.controller;

import java.util.List;
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

import com.example.CrowdFunding.CrowdFundingBackend.model.Donor;
import com.example.CrowdFunding.CrowdFundingBackend.service.implementation.DonorServiceimplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/donor")
public class DonorController {
    
    @Autowired
    private DonorServiceimplementation donorServiceImplementation;

    //swagger documentation
	@Operation(summary = "Get Donor", description = "This is the api endpoint for retriving donor's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    // To get all the users 
    @GetMapping()
    public ResponseEntity<List<Donor>> getAllDonors(){
        List<Donor> donors = donorServiceImplementation.getAllDonors();
        if(donors != null)
            return new ResponseEntity<>(donors, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    //swagger documentation
	@Operation(summary = "Get Donor by their id", description = "This is the api endpoint for retriving donor's details by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    //To get users by id
    @GetMapping("/{donorId}")
    public ResponseEntity<Donor> getUserById(@PathVariable int donorId){
        Optional<Donor> donorOptional = donorServiceImplementation.getDonorById(donorId);
        if (donorOptional.isPresent()) {
            Donor donor = donorOptional.get();
            return ResponseEntity.ok().body(donor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //swagger documentation
	@Operation(summary = "Create Donor", description = "This is the api endpoint for creating the donor(user who is going to donate)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    //To insert data into user entity
    @PostMapping
    public ResponseEntity<Donor> createUser(@RequestBody Donor donor){
        Donor createdDonor = donorServiceImplementation.createDonor(donor);
        if(createdDonor !=null)
            return new ResponseEntity<>(createdDonor, HttpStatus.CREATED);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    //swagger documentation
	@Operation(summary = "Update Donor", description = "This is the api endpoint for updating donor's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    //To update the user
    @PutMapping("/{donorId}")
    public ResponseEntity<Donor> updateDonor(@PathVariable int donorId, @RequestBody Donor donor){
        Donor updatedDonor = donorServiceImplementation.updateDonor(donorId, donor);
        if(updatedDonor != null)
            return new ResponseEntity<>(updatedDonor, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //swagger documentation
	@Operation(summary = "Delete Donor", description = "This is the api endpoint for deleting donor's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    //To delete the user details with userId
    @DeleteMapping("/{donorId}")
    public ResponseEntity<Void> deleteDonor(@PathVariable int donorId){
        donorServiceImplementation.deleteDonor(donorId);
        return ResponseEntity.noContent().build();
    }
}
