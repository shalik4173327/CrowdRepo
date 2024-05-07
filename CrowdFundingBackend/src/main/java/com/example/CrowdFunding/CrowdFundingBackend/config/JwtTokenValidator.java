package com.example.CrowdFunding.CrowdFundingBackend.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                // Extract JWT token from the Authorization header (Bearer format)
            String jwt =request.getHeader(JwtConstant.JWT_HEADER);
            if (jwt != null) {
                // Remove "Bearer " prefix if present
                jwt=jwt.substring(7);			
    
                try {
                    // Parse the JWT token using a secret key
                    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
    
                    // Extract email and authorities from claims
                    String email = String.valueOf(claims.get("email"));
                    String authorities = String.valueOf(claims.get("authorities"));
    
                    System.out.println("authorities -------- "+authorities);
				
                    // Convert comma-separated string to a list of GrantedAuthority objects
                    List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    
                    // Create an authentication object with extracted information
                    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
    
                    // Set the authentication object in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
    
                } catch (Exception e) {
                    // Handle exceptions (e.g., invalid token) by throwing a BadCredentialsException
                    throw new BadCredentialsException("Invalid token...");
                }
            }
    
            // Continue processing the request through the filter chain
            filterChain.doFilter(request, response);
    }
    
}
