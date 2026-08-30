package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.Role;
import com.leadpilot.crm.enums.UserStatus;

/**
 * ==========================================================
 * DTO : UserResponse
 *
 * Description :
 * Returns complete user details to the Admin.
 * Password is never exposed.
 * ==========================================================
 */
public class UserResponse {

    private Long id;

    private String employeeId;

    private String fullName;

    private String username;

    private String email;

    private String phoneNumber;

    private Role role;

    private String designation;

    private UserStatus status;

    private String profileImage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private String department;

    private LocalDateTime lastLogin;

    // ==========================
    // Constructors
    // ==========================

    public UserResponse() {
    }

    public UserResponse(Long id, String employeeId, String fullName,
                        String username, String email, String phoneNumber,
                        Role role, String designation,
                        UserStatus status, String profileImage,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt,String department, LocalDateTime lastLogin) {

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
        this.department = department;
        this.lastLogin = lastLogin;
    }

    // ==========================
    // Getters and Setters
    // ==========================

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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
    

}