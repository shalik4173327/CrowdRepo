package com.example.CrowdFunding.CrowdFundingBackend.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
@Configuration
@EnableWebSecurity
public class ApplicationConfig {
    
    
//     private static final String[] WHITELIST = {
//         "/api/**",
//         "/swagger-ui/**",
//         "/swagger-ui.html/",
//         "/v3/api-docs/**"
// };

    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Configure stateless session management (avoids session cookies)
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(Authorize -> Authorize
                // Define authorization rules based on URL patterns and roles
                .requestMatchers("/api/users/**").hasAnyRole("USER")
                .requestMatchers("/api/admin/**").hasAnyRole("ADMIN")
                .requestMatchers("/api/all/**").hasAnyRole("CAMPAIGN","ADMIN","USER")
                // Require authentication for all other API endpoints under /api/**
                .requestMatchers("/api/**").authenticated()
                // Allow all other requests without any restrictions (consider tightening this in production)
                .anyRequest().permitAll()
                );
        // Add JWT token validation filter before BasicAuthenticationFilter
        http.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class);

        // Disable CSRF protection as JWT is stateless (may need to be enabled in specific cases)
        http.csrf(csrf -> csrf.disable());

        // Configure CORS with allowed origins, methods, headers, etc.
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    // CORS Configuration (origins, methods, headers, etc.)
    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            
            @Override
            @Nullable
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList(
                        
                        "http://localhost:8082"
                ));
                config.setAllowedMethods(Collections.singletonList("*")); // Allow all HTTP methods (consider restricting in production)
                config.setAllowCredentials(true);  // Allow cookies for CORS requests
                config.setAllowedHeaders(Collections.singletonList("*")); // Allow all headers (consider restricting in production)
                config.setExposedHeaders(Arrays.asList("Authorization"));  // Expose the Authorization header for client-side access
                config.setMaxAge(3600L);  // Set CORS cache duration to 1 hour
                return config;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use a strong password hashing algorithm
    }

}
