package com.example.CrowdFunding.CrowdFundingBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CrowdFunding.CrowdFundingBackend.config.JwtProvider;
import com.example.CrowdFunding.CrowdFundingBackend.domain.ROLE;
import com.example.CrowdFunding.CrowdFundingBackend.exception.UserException;
import com.example.CrowdFunding.CrowdFundingBackend.model.User;
import com.example.CrowdFunding.CrowdFundingBackend.repository.UserRepository;
import com.example.CrowdFunding.CrowdFundingBackend.request.LoginRequest;
import com.example.CrowdFunding.CrowdFundingBackend.response.AuthenticationResponse;
import com.example.CrowdFunding.CrowdFundingBackend.service.implementation.DonorUserServiceImplementation;
import com.example.CrowdFunding.CrowdFundingBackend.service.interfaces.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationContorller {


	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtProvider jwtProvider;
	private DonorUserServiceImplementation donorUserDetails;
	
    // private PasswordResetTokenService passwordResetTokenService;

    private UserService userService;

	// Constructor for dependency injection
	public AuthenticationContorller(UserRepository userRepository, 
			PasswordEncoder passwordEncoder, 
			JwtProvider jwtProvider,
            DonorUserServiceImplementation donorUserDetails,
			// CustomeUserServiceImplementation customUserDetails,
			// CartRepository cartRepository,
			// PasswordResetTokenService passwordResetTokenService,
			UserService userService
			) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.donorUserDetails = donorUserDetails;
		// this.cartRepository=cartRepository;
		// this.passwordResetTokenService=passwordResetTokenService;
		this.userService=userService;

	}

	//swagger documentation
	@Operation(summary = "For register user", description = "This is the api endpoint for registering the person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
	 // POST endpoint for user signup
	@PostMapping("/signup")
	public ResponseEntity<AuthenticationResponse> createUserHandler(@Validated @RequestBody User user) throws UserException {

		// Extract user details from the request
		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		ROLE role=user.getRole();
		
        // Check if the email is already registered
		User isEmailExist = userRepository.findByEmail(email);

		if (isEmailExist!=null) {

			throw new UserException("Email Is Already Used With Another Account");
		}

		// Create new user
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setRole(role);
		
		User savedUser = userRepository.save(createdUser);
		
		
        // Authenticate the user after successful signup
		List<GrantedAuthority> authorities=new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(role.toString()));


		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password,authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		// Prepare the response
		AuthenticationResponse authResponse = new AuthenticationResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Register Success");
		authResponse.setRole(savedUser.getRole());

		return new ResponseEntity<>(authResponse, HttpStatus.OK);

	}

	//swagger documentation
	@Operation(summary = "For user login", description = "This is the api endpoint for authorizing the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
	// POST endpoint for user signin
	@PostMapping("/signin")
	public ResponseEntity<AuthenticationResponse> signin(@RequestBody LoginRequest loginRequest) {

		// Extract username and password from the login request
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		System.out.println(username + " ----- " + password);

		// Authenticate the user
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		// Prepare the response
		AuthenticationResponse authResponse = new AuthenticationResponse();

		authResponse.setMessage("Login Success");
		authResponse.setJwt(token);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


		String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();


		authResponse.setRole(ROLE.valueOf(roleName));

		return new ResponseEntity<AuthenticationResponse>(authResponse, HttpStatus.OK);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = donorUserDetails.loadUserByUsername(username);

		System.out.println("sign in userDetails - " + userDetails);

		if (userDetails == null) {
			System.out.println("sign in userDetails - null " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			System.out.println("sign in userDetails - password not match " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	// @PostMapping("/reset-password")
	//  public ResponseEntity<ApiResponse> resetPassword(
	    		
	//     		@RequestBody ResetPasswordRequest req) throws UserException {
	        
	//         PasswordResetToken resetToken = passwordResetTokenService.findByToken(req.getToken());

	//         if (resetToken == null ) {
	//         	throw new UserException("token is required...");
	//         }
	//         if(resetToken.isExpired()) {
	//         	passwordResetTokenService.delete(resetToken);
	//         	throw new UserException("token get expired...");
	        
	//         }

	        // Update user's password
	    //     User user = resetToken.getUser();
	    //     userService.updatePassword(user, req.getPassword());

	    //     // Delete the token
	    //     passwordResetTokenService.delete(resetToken);
	        
	    //     ApiResponse res=new ApiResponse();
	    //     res.setMessage("Password updated successfully.");
	    //     res.setStatus(true);

	    //     return ResponseEntity.ok(res);
	    // }
	 
	//  @PostMapping("/reset-password-request")
	//     public ResponseEntity<ApiResponse> resetPassword(@RequestParam("email") String email) throws UserException {
	//         User user = userService.findUserByEmail(email);
	//         System.out.println("ResetPasswordController.resetPassword()");

	//         if (user == null) {
	//         	throw new UserException("user not found");
	//         }

	//         userService.sendPasswordResetEmail(user);

	//         ApiResponse res=new ApiResponse();
	//         res.setMessage("Password reset email sent successfully.");
	//         res.setStatus(true);

	//         return ResponseEntity.ok(res);
	//     }

}
