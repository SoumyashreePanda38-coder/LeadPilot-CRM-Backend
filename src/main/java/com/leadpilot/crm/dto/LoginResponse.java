package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.Role;

/**
 * ==========================================================
 * DTO : LoginResponse
 *
 * Description :
 * Returns user information after successful login.
 * ==========================================================
 */

public class LoginResponse {

    private Long id;

    private String employeeId;

    private String fullName;

    private String username;

    private String email;

    private Role role;

    private String designation;

    private String profileImage;

    private String message;
    
    private String token;

    // ==========================================================
    // Constructors
    // ==========================================================

    public LoginResponse() {
    }

    public LoginResponse(Long id,
                         String employeeId,
                         String fullName,
                         String username,
                         String email,
                         Role role,
                         String designation,
                         String profileImage,
                         String message,
                         String token) {

        this.id = id;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.role = role;
        this.designation = designation;
        this.profileImage = profileImage;
        this.message = message;
        this.token = token;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    

}