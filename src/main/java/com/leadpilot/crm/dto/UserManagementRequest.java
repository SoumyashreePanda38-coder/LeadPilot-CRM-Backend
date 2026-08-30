package com.leadpilot.crm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : UserManagementRequest
 *
 * Description :
 * Request DTO used by ADMIN to create or update
 * EXECUTIVE users.
 *
 * Role is intentionally NOT included.
 *
 * The backend automatically assigns:
 *
 *     Role.EXECUTIVE
 *
 * ==========================================================
 */
public class UserManagementRequest {

    // ==========================================================
    // Employee ID
    // ==========================================================

    @NotBlank(message = "Employee ID is required")
    @Size(max = 20, message = "Employee ID cannot exceed 20 characters")
    private String employeeId;

    // ==========================================================
    // Full Name
    // ==========================================================

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    // ==========================================================
    // Username
    // ==========================================================

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String username;

    // ==========================================================
    // Email
    // ==========================================================

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    // ==========================================================
    // Phone Number
    // ==========================================================

    @NotBlank(message = "Phone number is required")
    @Size(max = 15, message = "Phone number cannot exceed 15 characters")
    private String phoneNumber;

    // ==========================================================
    // Password
    // ==========================================================

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100,
            message = "Password must be between 6 and 100 characters")
    private String password;

    // ==========================================================
    // Designation
    // ==========================================================

    @Size(max = 100,
            message = "Designation cannot exceed 100 characters")
    private String designation;

    // ==========================================================
    // Department
    // ==========================================================

    @Size(max = 100,
            message = "Department cannot exceed 100 characters")
    private String department;

    // ==========================================================
    // Getters and Setters
    // ==========================================================

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}