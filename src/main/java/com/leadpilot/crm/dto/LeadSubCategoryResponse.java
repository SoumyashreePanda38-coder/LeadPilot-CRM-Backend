package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * DTO : LeadSubCategoryResponse
 *
 * Description :
 * Used to send lead subcategory information to the frontend.
 * Includes parent category and audit information.
 * ==========================================================
 */
public class LeadSubCategoryResponse {

    // ==========================================================
    // Primary Key
    // ==========================================================

    private Long subCategoryId;

    // ==========================================================
    // Parent Category
    // ==========================================================

    private Long categoryId;

    private String categoryName;

    // ==========================================================
    // Subcategory Information
    // ==========================================================

    private String subCategoryName;

    private String description;

    private Integer displayOrder;

    // ==========================================================
    // Status
    // ==========================================================

    private CategoryStatus status;

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
    // Constructors
    // ==========================================================

    public LeadSubCategoryResponse() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
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

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
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