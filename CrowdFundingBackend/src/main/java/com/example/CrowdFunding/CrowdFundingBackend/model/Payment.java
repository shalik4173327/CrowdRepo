package com.example.CrowdFunding.CrowdFundingBackend.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentid;
    private double amount;
    private Date paymentDate = new Date();

    @ManyToOne
    @JoinColumn(name = "email")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "campaign_title")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;


}
