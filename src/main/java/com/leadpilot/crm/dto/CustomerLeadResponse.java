package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.LeadPriority;
import com.leadpilot.crm.enums.LeadStatus;

/**
 * ==========================================================
 * DTO : CustomerLeadResponse
 *
 * Description :
 * Used to return customer lead information to the frontend.
 * ==========================================================
 */
public class CustomerLeadResponse {

    // ==========================================================
    // Primary Key
    // ==========================================================

    private Long leadId;

    // ==========================================================
    // Customer / Lead Personal Information
    // ==========================================================

    private String fullName;
    private Integer age;

    // ==========================================================
    // Contact Information
    // ==========================================================

    private String email;
    private String phoneNumber;

    // ==========================================================
    // Address Information
    // ==========================================================

    private String address;
    private String city;
    private String state;
    private String pincode;

    // ==========================================================
    // Category
    // ==========================================================

    private Long categoryId;
    private String categoryName;

    // ==========================================================
    // Subcategory
    // ==========================================================

    private Long subCategoryId;
    private String subCategoryName;

    // ==========================================================
    // Lead Source
    // ==========================================================

    private Long leadSourceId;
    private String sourceName;

    // ==========================================================
    // Lead Status
    // ==========================================================

    private LeadStatus leadStatus;

    // ==========================================================
    // Lead Priority
    // ==========================================================

    private LeadPriority leadPriority;

    // ==========================================================
    // Assigned Executive
    // ==========================================================

    private Long assignedUserId;
    private String assignedUserName;

    // ==========================================================
    // Audit Information
    // ==========================================================

    private Long createdById;
    private String createdByName;

    private Long updatedById;
    private String updatedByName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ==========================================================
    // Constructor
    // ==========================================================

    public CustomerLeadResponse() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Long getLeadSourceId() {
        return leadSourceId;
    }

    public void setLeadSourceId(Long leadSourceId) {
        this.leadSourceId = leadSourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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

    public String getAssignedUserName() {
        return assignedUserName;
    }

    public void setAssignedUserName(String assignedUserName) {
        this.assignedUserName = assignedUserName;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
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
}