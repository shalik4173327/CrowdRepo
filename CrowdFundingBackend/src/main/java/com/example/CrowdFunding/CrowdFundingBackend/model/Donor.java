package com.example.CrowdFunding.CrowdFundingBackend.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity

public class Donor {
        
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfJoining;         //date is autogenerate at time of creating the user so using @PerPersist
    private String contact;
    private Double contributionAmount;

    //getters and setters for every attribute
    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    @PrePersist
    public void setDateOfJoining() {
        this.dateOfJoining = new Date();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(Double contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

 
    // Relationship with Payment 
    @OneToMany(mappedBy = "donor")
    private List<Payment> payments;
    
    // Relationship with Campaign
    // @ManyToMany(mappedBy = "donors")
    // private Set<Campaign> campaigns = new HashSet<>();

}
