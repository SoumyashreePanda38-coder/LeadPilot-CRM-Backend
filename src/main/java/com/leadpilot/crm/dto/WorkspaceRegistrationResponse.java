package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : WorkspaceRegistrationResponse
 *
 * Description :
 * Represents the response returned after successfully
 * creating a new workspace and its first Admin user.
 * ==========================================================
 */

public class WorkspaceRegistrationResponse {

    // ==========================================================
    // Organization Details
    // ==========================================================

    private Long organizationId;

    private String organizationName;


    // ==========================================================
    // Admin Details
    // ==========================================================

    private Long adminId;

    private String employeeId;

    private String fullName;

    private String username;

    private String email;

    private String phoneNumber;

    private String designation;

    private String role;


    // ==========================================================
    // Response Message
    // ==========================================================

    private String message;


    // ==========================================================
    // Constructors
    // ==========================================================

    public WorkspaceRegistrationResponse() {
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }


    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}