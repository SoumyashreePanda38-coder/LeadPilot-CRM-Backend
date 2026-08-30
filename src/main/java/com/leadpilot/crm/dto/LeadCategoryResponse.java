package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * DTO : LeadCategoryResponse
 *
 * Description :
 * Used to send lead category information to the frontend.
 * Includes category details and audit information.
 * ==========================================================
 */
public class LeadCategoryResponse {

    // ==========================================================
    // Primary Key
    // ==========================================================

    private Long categoryId;

    // ==========================================================
    // Category Information
    // ==========================================================

    private String categoryName;

    private String description;

    private Integer displayOrder;

    // ==========================================================
    // Category Status
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

    public LeadCategoryResponse() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

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