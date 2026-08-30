
package com.leadpilot.crm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.leadpilot.crm.dto.ChangePasswordRequest;
import com.leadpilot.crm.dto.LoginRequest;
import com.leadpilot.crm.dto.LoginResponse;
import com.leadpilot.crm.dto.UserProfileResponse;
import com.leadpilot.crm.service.AuthService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * ==========================================================
 * Controller : AuthController
 *
 * Description :
 * Handles authentication related APIs such as
 * Login, Logout, Change Password and Profile.
 *
 * Base URL :
 * /api/auth
 *
 * ==========================================================
 */

@RestController
@RequestMapping("/api/auth")
@Validated
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200"
})
@Tag(
        name = "Authentication",
        description = "APIs for user authentication, logout, password management, and profile information."
)
public class AuthController {

    // ==========================================================
    // Dependency Injection
    // ==========================================================

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ==========================================================
    // Login
    // ==========================================================

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user using their username and password and returns the login response containing authenticated user information."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {

        LoginResponse response =
                authService.login(loginRequest);

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // Logout
    // ==========================================================

    @Operation(
            summary = "Logout current user",
            description = "Logs out the currently authenticated user and returns a confirmation message."
    )
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok(
                authService.logout()
        );
    }

    // ==========================================================
    // Change Password
    // ==========================================================

    @Operation(
            summary = "Change user password",
            description = "Changes the password of the currently authenticated user after validating the provided password information."
    )
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        return ResponseEntity.ok(
                authService.changePassword(request)
        );
    }

    // ==========================================================
    // Get Logged-in User Profile
    // ==========================================================

    @Operation(
            summary = "Get logged-in user profile",
            description = "Retrieves the profile information of the currently authenticated user."
    )
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile() {

        UserProfileResponse response =
                authService.getLoggedInUserProfile();

        return ResponseEntity.ok(response);
    }
}