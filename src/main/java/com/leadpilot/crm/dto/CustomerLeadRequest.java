package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.LeadPriority;
import com.leadpilot.crm.enums.LeadStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : CustomerLeadRequest
 *
 * Description :
 * Used to receive customer lead information from the frontend.
 * ==========================================================
 */
public class CustomerLeadRequest {

    // ==========================================================
    // Customer / Lead Personal Information
    // ==========================================================

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    private Integer age;

    // ==========================================================
    // Contact Information
    // ==========================================================

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 15, message = "Phone number cannot exceed 15 characters")
    private String phoneNumber;

    // ==========================================================
    // Address Information
    // ==========================================================

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State cannot exceed 100 characters")
    private String state;

    @Size(max = 10, message = "Pincode cannot exceed 10 characters")
    private String pincode;

    // ==========================================================
    // Category / Subcategory / Source
    // ==========================================================

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Subcategory is required")
    private Long subCategoryId;

    @NotNull(message = "Lead source is required")
    private Long leadSourceId;

    // ==========================================================
    // Lead Status
    // ==========================================================

    @NotNull(message = "Lead status is required")
    private LeadStatus leadStatus;

    // ==========================================================
    // Lead Priority
    // ==========================================================

    @NotNull(message = "Lead priority is required")
    private LeadPriority leadPriority;

    // ==========================================================
    // Assigned Executive
    // ==========================================================

    private Long assignedUserId;

    // ==========================================================
    // Constructor
    // ==========================================================

    public CustomerLeadRequest() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Long getLeadSourceId() {
        return leadSourceId;
    }

    public void setLeadSourceId(Long leadSourceId) {
        this.leadSourceId = leadSourceId;
    }

    public LeadStatus getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(LeadStatus leadStatus) {
        this.leadStatus = leadStatus;
    }

    public LeadPriority getLeadPriority() {
        return leadPriority;
    }

    public void setLeadPriority(LeadPriority leadPriority) {
        this.leadPriority = leadPriority;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
}