package com.example.CrowdFunding.CrowdFundingBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CrowdFunding.CrowdFundingBackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    public User findByEmail(String username);

}
