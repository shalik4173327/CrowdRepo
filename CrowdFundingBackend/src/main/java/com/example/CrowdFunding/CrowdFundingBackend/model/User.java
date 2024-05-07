package com.example.CrowdFunding.CrowdFundingBackend.model;

import com.example.CrowdFunding.CrowdFundingBackend.domain.ROLE;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String fullName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ROLE role;

}
