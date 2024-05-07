package com.example.CrowdFunding.CrowdFundingBackend.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @Column(nullable = false)
    private String adminName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // @OneToMany
    // private Set<Campaign> campaigns = new HashSet<>();

    // @OneToMany
    // private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private Set<Campaign> campaigns = new HashSet<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private Set<Payment> payments = new HashSet<>();

}
