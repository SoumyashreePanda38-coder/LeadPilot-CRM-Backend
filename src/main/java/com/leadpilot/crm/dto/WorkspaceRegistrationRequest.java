package com.leadpilot.crm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : WorkspaceRegistrationRequest
 *
 * Description :
 * Accepts the information required to create a new
 * organization/workspace and its first Admin user.
 *
 * The role is NOT accepted from the frontend.
 * The backend automatically assigns ADMIN role.
 *
 * Employee ID is also generated automatically by the backend.
 * ==========================================================
 */

public class WorkspaceRegistrationRequest {

    // ==========================================================
    // Organization / Workspace Details
    // ==========================================================

    @NotBlank(message = "Organization name is required")
    @Size(
        min = 2,
        max = 150,
        message = "Organization name must be between 2 and 150 characters"
    )
    private String organizationName;


    // ==========================================================
    // Admin Details
    // ==========================================================

    @NotBlank(message = "Full name is required")
    @Size(
        min = 2,
        max = 100,
        message = "Full name must be between 2 and 100 characters"
    )
    private String fullName;


    @NotBlank(message = "Username is required")
    @Size(
        min = 3,
        max = 50,
        message = "Username must be between 3 and 50 characters"
    )
    private String username;


    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(
        max = 100,
        message = "Email must not exceed 100 characters"
    )
    private String email;


    @NotBlank(message = "Phone number is required")
    @Size(
        min = 10,
        max = 15,
        message = "Phone number must be between 10 and 15 characters"
    )
    private String phoneNumber;


    @Size(
        max = 100,
        message = "Designation must not exceed 100 characters"
    )
    private String designation;


    // ==========================================================
    // Password
    // ==========================================================

    @NotBlank(message = "Password is required")
    @Size(
        min = 8,
        max = 100,
        message = "Password must be between 8 and 100 characters"
    )
    private String password;


    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;


    // ==========================================================
    // Constructors
    // ==========================================================

    public WorkspaceRegistrationRequest() {
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}