package com.example.CrowdFunding.CrowdFundingBackend.service.interfaces;

import java.util.List;

import com.example.CrowdFunding.CrowdFundingBackend.exception.UserException;
import com.example.CrowdFunding.CrowdFundingBackend.model.User;

public interface UserService {
    
    public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;

	public List<User> findAllUsers();
}
