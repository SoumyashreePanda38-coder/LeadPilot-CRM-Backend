package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.Role;
import com.leadpilot.crm.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : UserUpdateRequest
 *
 * Description :
 * Used by the Admin to update an existing user's details.
 * Employee ID, Username and Password cannot be updated
 * through this DTO.
 * ==========================================================
 */

public class UserUpdateRequest {

    @NotBlank(message = "Full name is required.")
    @Size(max = 100, message = "Full name cannot exceed 100 characters.")
    private String fullName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please enter a valid email address.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits."
    )
    private String phoneNumber;

    @NotNull(message = "Role is required.")
    private Role role;

    @Size(max = 100, message = "Designation cannot exceed 100 characters.")
    private String designation;

    @NotNull(message = "User status is required.")
    private UserStatus status;

    private String profileImage;
    
    @Size(max = 100, message = "Department cannot exceed 100 characters.")
    private String department;

    // ==========================================================
    // Constructors
    // ==========================================================

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String fullName,
                             String email,
                             String phoneNumber,
                             Role role,
                             String designation,
                             UserStatus status,
                             String profileImage,String department) {

        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.designation = designation;
        this.status = status;
        this.profileImage = profileImage;
        this.department = department;
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
    

}