package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.CategoryStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : LeadSubCategoryRequest
 *
 * Description :
 * Used to receive lead subcategory information from
 * the frontend.
 * ==========================================================
 */
public class LeadSubCategoryRequest {

    // ==========================================================
    // Parent Category
    // ==========================================================

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    // ==========================================================
    // Subcategory Information
    // ==========================================================

    @NotBlank(message = "Subcategory name is required")
    @Size(max = 100, message = "Subcategory name cannot exceed 100 characters")
    private String subCategoryName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Min(value = 1, message = "Display order must be greater than 0")
    private Integer displayOrder;

    // ==========================================================
    // Status
    // ==========================================================

    @NotNull(message = "Subcategory status is required")
    private CategoryStatus status;

    // ==========================================================
    // Constructors
    // ==========================================================

    public LeadSubCategoryRequest() {
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
}