package com.example.CrowdFunding.CrowdFundingBackend.service.implementation;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.CrowdFunding.CrowdFundingBackend.config.JwtProvider;
import com.example.CrowdFunding.CrowdFundingBackend.exception.UserException;
import com.example.CrowdFunding.CrowdFundingBackend.model.User;
import com.example.CrowdFunding.CrowdFundingBackend.repository.UserRepository;
import com.example.CrowdFunding.CrowdFundingBackend.service.interfaces.UserService;

@Service
public class UserServiceImplementation implements UserService{
    
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        // Validate JWT token before extracting email (security enhancement)
        if (!jwtProvider.validateJwtToken(jwt)) {
            throw new UserException("Invalid JWT token");
        }

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String username) throws UserException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return user;
        }
        throw new UserException("User not found with email: " + username);
    }
}
