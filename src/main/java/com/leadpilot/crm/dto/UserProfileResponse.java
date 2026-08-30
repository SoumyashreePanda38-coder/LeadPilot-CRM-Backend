package com.leadpilot.crm.dto;


import java.time.LocalDateTime;

import com.leadpilot.crm.enums.Role;
import com.leadpilot.crm.enums.UserStatus;

/**
 * ==========================================================
 * DTO : UserProfileResponse
 *
 * Description :
 * Returns the profile information of the logged-in user.
 * Password is intentionally excluded for security reasons.
 * ==========================================================
 */

public class UserProfileResponse {

    // ==========================================================
    // User Information
    // ==========================================================

    private Long id;

    private String employeeId;

    private String fullName;

    private String username;

    private String email;

    private String phoneNumber;

    // ==========================================================
    // Role Information
    // ==========================================================

    private Role role;

    private String designation;

    private UserStatus status;

    // ==========================================================
    // Profile Information
    // ==========================================================

    private String profileImage;

    // ==========================================================
    // Audit Information
    // ==========================================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================================================
    // Constructors
    // ==========================================================

    public UserProfileResponse() {
    }

    public UserProfileResponse(
            Long id,
            String employeeId,
            String fullName,
            String username,
            String email,
            String phoneNumber,
            Role role,
            String designation,
            UserStatus status,
            String profileImage,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.designation = designation;
        this.status = status;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}