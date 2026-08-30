package com.leadpilot.crm.serviceImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leadpilot.crm.dto.ChangePasswordRequest;
import com.leadpilot.crm.dto.LoginRequest;
import com.leadpilot.crm.dto.LoginResponse;
import com.leadpilot.crm.dto.UserProfileResponse;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.exception.BadRequestException;
import com.leadpilot.crm.exception.ResourceNotFoundException;
import com.leadpilot.crm.mapper.UserMapper;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.security.JwtService;
import com.leadpilot.crm.service.AuthService;

/**
 * ==========================================================
 * Service Implementation : AuthServiceImpl
 *
 * Description :
 * Handles all authentication related operations including
 * login, logout, password change and profile retrieval.
 *
 * Author : LeadPilot CRM
 * ==========================================================
 */

@Service
public class AuthServiceImpl implements AuthService {

    // ==========================================================
    // Dependencies
    // ==========================================================

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    // ==========================================================
    // Constructor Injection
    // ==========================================================

    public AuthServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // ==========================================================
    // Login
    // ==========================================================

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

    	authenticationManager.authenticate(

    	        new UsernamePasswordAuthenticationToken(

    	                loginRequest.getUsername(),

    	                loginRequest.getPassword()

    	        )
    	);
    	
    	User user = getUserByUsername(loginRequest.getUsername());

    	// Generate JWT Token
    	String token = jwtService.generateToken(
    	        user.getUsername(),
    	        user.getRole().name()
    	);
    	// Convert Entity to DTO
    	LoginResponse response = userMapper.toLoginResponse(user);

    	// Set JWT Token
    	response.setToken(token);

    	// Success Message
    	response.setMessage("Login successful.");

    	return response;
    }

    // ==========================================================
    // Logout
    // ==========================================================

    @Override
    public String logout() {

        /*
         * JWT Logout:
         *
         * At present we are not maintaining any server-side session.
         * Logout will simply be handled by removing the JWT token
         * from the Angular application.
         *
         * Later if Refresh Tokens are implemented,
         * this method can blacklist the token.
         */

        return "Logout successful.";
    }
    // ==========================================================
    // Change Password
    // ==========================================================

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {

        User user = getCurrentUser();

        // Verify current password
        if (!passwordEncoder.matches(
                changePasswordRequest.getCurrentPassword(),
                user.getPassword())) {

            throw new BadRequestException("Current password is incorrect.");
        }

        // Check new password and confirm password
        if (!changePasswordRequest.getNewPassword()
                .equals(changePasswordRequest.getConfirmPassword())) {

            throw new BadRequestException(
                    "New password and confirm password do not match.");
        }

        // Prevent using the same password
        if (passwordEncoder.matches(
                changePasswordRequest.getNewPassword(),
                user.getPassword())) {

            throw new BadRequestException(
                    "New password cannot be the same as the current password.");
        }

        // Encrypt and save new password
        user.setPassword(
                passwordEncoder.encode(
                        changePasswordRequest.getNewPassword()));

        userRepository.save(user);

        return "Password changed successfully.";
    }

    // ==========================================================
    // Get Logged-in User Profile
    // ==========================================================

    @Override
    public UserProfileResponse getLoggedInUserProfile() {

        User user = getCurrentUser();

        return userMapper.toUserProfileResponse(user);
    }
    // ==========================================================
    // Private Helper Methods
    // ==========================================================

    /**
     * ==========================================================
     * Returns the currently authenticated user.
     *
     * This method is shared by multiple service methods
     * to avoid duplicate code.
     * ==========================================================
     */
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // Check whether a user is authenticated
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {

            throw new BadRequestException("No authenticated user found.");
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."));
    }

    /**
     * ==========================================================
     * Returns the user by username.
     *
     * This helper method can be reused in future modules
     * like JWT Authentication, Forgot Password,
     * Email Verification, etc.
     * ==========================================================
     */
    private User getUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."));
    }

}