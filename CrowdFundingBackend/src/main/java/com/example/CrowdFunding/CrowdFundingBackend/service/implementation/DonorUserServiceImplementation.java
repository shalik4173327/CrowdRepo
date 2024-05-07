package com.example.CrowdFunding.CrowdFundingBackend.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.CrowdFunding.CrowdFundingBackend.domain.ROLE;
import com.example.CrowdFunding.CrowdFundingBackend.model.User;
import com.example.CrowdFunding.CrowdFundingBackend.repository.UserRepository;

@Service
public class DonorUserServiceImplementation implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("user not found with email  - "+username);
        }

        ROLE role = user.getRole();
        
        if(role == null) role = ROLE.ROLE_USER;

        System.out.println("role ------"+role);

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(
				user.getEmail(),user.getPassword(),authorities);
        
    }
    
}
