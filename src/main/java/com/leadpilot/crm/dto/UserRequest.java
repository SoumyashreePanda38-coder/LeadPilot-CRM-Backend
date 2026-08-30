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
 * DTO : UserRequest
 *
 * Description :
 * Used by the Admin to create a new Admin or Executive.
 * ==========================================================
 */
public class UserRequest {

    @NotBlank(message = "Employee ID is required.")
    @Size(max = 20, message = "Employee ID cannot exceed 20 characters.")
    private String employeeId;

    @NotBlank(message = "Full name is required.")
    @Size(max = 100, message = "Full name cannot exceed 100 characters.")
    private String fullName;

    @NotBlank(message = "Username is required.")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

    @NotBlank(message = "Email is required.")
    @Email(message = "Enter a valid email address.")
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

    // ==========================
    // Constructors
    // ==========================

    public UserRequest() {
    }

    public UserRequest(String employeeId, String fullName, String username,
                       String password, String email, String phoneNumber,
                       Role role, String designation,
                       UserStatus status, String profileImage, String department) {

        this.employeeId = employeeId;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.designation = designation;
        this.status = status;
        this.profileImage = profileImage;
        this.department = department;
    }

    // ==========================
    // Getters and Setters
    // ==========================

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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