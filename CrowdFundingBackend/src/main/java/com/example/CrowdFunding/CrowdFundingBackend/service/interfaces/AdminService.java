package com.example.CrowdFunding.CrowdFundingBackend.service.interfaces;

import java.util.List;

import com.example.CrowdFunding.CrowdFundingBackend.model.Admin;

public interface  AdminService {
    
    public Admin saveAdmin(Admin admin) ;

    public List<Admin> getAllAdmins() ;

    public Admin getAdminById(Integer adminId);
}
