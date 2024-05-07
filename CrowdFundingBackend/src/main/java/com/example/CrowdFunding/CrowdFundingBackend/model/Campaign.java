package com.example.CrowdFunding.CrowdFundingBackend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    
       
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int campaignId;
    private String title;
    private String description;
    private double goalAmount;
    private double raisedAmount;
    private Date startDate = new Date();
    private Date endDate;
    private String category;

    
    // To update the status of campaign
    private String status = "Active";

    @PreUpdate
    @PrePersist
    protected void updateStatus(){
        if(raisedAmount >= goalAmount)
            status = "Completed";
    }

    // Relationship with payment
    @OneToMany(mappedBy = "campaign")
    private Set<Payment> payments = new HashSet<>();

    // Relationship with admin
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // Relationship with donor
    // @ManyToMany
    // private Set<Donor> donors = new HashSet<>();

    // Calculate the raised amount
    public void updateRaisedAmount() {
        double totalContributions = 0.0;
        for (Payment payment : payments) {
            totalContributions += payment.getAmount();
        }
        this.raisedAmount = totalContributions;
    }

    //To calculate pending amount
    // As pendingAmount is dynamically changin
    @Transient
    private double pendingAmount;

    public double getPendingAmount(){
        return (goalAmount - raisedAmount);
    }
    
}
