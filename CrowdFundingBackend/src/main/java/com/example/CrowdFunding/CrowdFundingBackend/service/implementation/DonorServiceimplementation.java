package com.example.CrowdFunding.CrowdFundingBackend.service.implementation;


import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CrowdFunding.CrowdFundingBackend.model.Donor;
import com.example.CrowdFunding.CrowdFundingBackend.repository.DonorRepository;
import com.example.CrowdFunding.CrowdFundingBackend.service.interfaces.DonorService;

@Service
public class DonorServiceimplementation implements DonorService{
 
    //Connecting the service layer to repository layer
    @Autowired
    private DonorRepository donorRepository;

    @Override
    //To get all the user details. like select * from users;
    public List<Donor> getAllDonors(){
        return donorRepository.findAll();
    }

    @Override
    //To get userdetails by their id
    public Optional<Donor> getDonorById(int userId){
        return donorRepository.findById(userId);
    }

    @Override
    //To insert details into user entity
    public Donor createDonor(Donor donor){

        // To validate the contact number
        if (!isContactValid(donor.getContact())) {
            throw new IllegalArgumentException("Invalid contact format");
        }

        // To validate the email address.
        if (!isEmailValid(donor.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // To check if the email address is already present or not
        if (isEmailExists(donor.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return donorRepository.save(donor);
    }

    @Override
    //To update user details by userId.
    public Donor updateDonor(int donorId, Donor newDonor) {
        Optional<Donor> existingUserById = donorRepository.findById(donorId);
        if (existingUserById.isPresent()) {
            Donor existingUser = existingUserById.get();
            existingUser.setFirstName(newDonor.getFirstName());
            existingUser.setLastName(newDonor.getLastName());
            existingUser.setEmail(newDonor.getEmail());
            existingUser.setPassword(newDonor.getPassword());
            existingUser.setContact(newDonor.getContact());
            existingUser.setContributionAmount(newDonor.getContributionAmount());
            return donorRepository.save(existingUser);
        } else {
            return null;
        }
    }
    

    @Override
    // Deleteing the user details by userId using deleteById()
    public void deleteDonor(int donorId){
        donorRepository.deleteById(donorId);
    }

    private boolean isEmailValid(String email) {
        // Regular expression pattern for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        
        // Compile the pattern into a regular expression object
        Pattern pattern = Pattern.compile(emailRegex);
        
        // Match the input email against the pattern
        Matcher matcher = pattern.matcher(email);
        
        // Return true if the email matches the pattern, indicating it's valid
        return matcher.matches();
    }

    private boolean isEmailExists(String email) {
        //Return true if email address already exisits
        Optional<Donor> userOptional = donorRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    private boolean isContactValid(String contact) {
        // Check if contact number is null or empty or not a 9 digit number
        if (contact == null || contact.isEmpty() || contact.length() != 10) {
            return false; // Contact number is null or empty
        }
    
        // Check if contact number contains only digits
        for (char c : contact.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false; 
            }
        }
        // All validation checks passed, contact number is valid
        return true;
    }
    

}
