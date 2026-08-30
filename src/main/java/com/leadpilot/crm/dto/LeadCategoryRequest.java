package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.CategoryStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : LeadCategoryRequest
 *
 * Description :
 * Used to receive lead category information from the frontend.
 * ==========================================================
 */
public class LeadCategoryRequest {

    // ==========================================================
    // Category Information
    // ==========================================================

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Category name cannot exceed 100 characters")
    private String categoryName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Min(value = 1, message = "Display order must be greater than 0")
    private Integer displayOrder;

    // ==========================================================
    // Category Status
    // ==========================================================

    @NotNull(message = "Category status is required")
    private CategoryStatus status;

    // ==========================================================
    // Constructors
    // ==========================================================

    public LeadCategoryRequest() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

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
}