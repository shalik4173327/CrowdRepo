package com.example.CrowdFunding.CrowdFundingBackend.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CrowdFunding.CrowdFundingBackend.model.Admin;
import com.example.CrowdFunding.CrowdFundingBackend.repository.AdminRepository;
import com.example.CrowdFunding.CrowdFundingBackend.service.interfaces.AdminService;

@Service
public class AdminServiceImplementation implements AdminService{
    
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }
}
