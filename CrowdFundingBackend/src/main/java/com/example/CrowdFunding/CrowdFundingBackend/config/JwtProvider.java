package com.example.CrowdFunding.CrowdFundingBackend.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
    
        // Assuming JwtConstant.SECRET_KEY is a secure secret key used for signing JWT tokens
        private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

        public String generateToken(Authentication auth) {
            // Extract authorities (roles) from the authentication object
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            String roles = populateAuthorities(authorities);  // Convert authorities to a comma-separated string

            // Build the JWT token
            String jwt = Jwts.builder()
                    .setIssuedAt(new Date())                               // Set issue time
                    .setExpiration(new Date(new Date().getTime() + 1000*60*96))  // Set expiration time (3 day)
                    .claim("email", auth.getName())                            // Set email claim (assuming username is stored in name)
                    .claim("authorities", roles)                             // Set authorities claim
                    .signWith(key)                   // Sign the token with the secret key using HS256 algorithm
                    .compact();                                               // Generate the compact JWT string

            return jwt;
        }

        public String getEmailFromJwtToken(String jwt) {
            // Remove "Bearer " prefix if present (modify if different prefix is used)
            jwt=jwt.substring(7);
            // Parse the JWT token using the secret key
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

            // Extract email claim from the parsed token
            String email = String.valueOf(claims.get("email"));

            return email;
        }

        public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
            // Create a set to store authority strings (roles)
            Set<String> auths = new HashSet<>();

            // Iterate through the authorities collection and add each authority string to the set
            for (GrantedAuthority authority : collection) {
                auths.add(authority.getAuthority());
            }

            // Convert the set of authorities to a comma-separated string
            return String.join(",", auths);
        }

        public boolean validateJwtToken(String jwt) {
            
                // Remove "Bearer " prefix if present (modify if different prefix is used)
                jwt=jwt.substring(7);
        
                // Parse the JWT token using the secret key
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
                return true;  // If no exception occurs, the token is considered valid
            
        }

}
