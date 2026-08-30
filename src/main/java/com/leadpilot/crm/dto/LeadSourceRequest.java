package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.CategoryStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : LeadSourceRequest
 *
 * Description :
 * Used to receive lead source information from the frontend.
 * ==========================================================
 */
public class LeadSourceRequest {

    // ==========================================================
    // Source Information
    // ==========================================================

    @NotBlank(message = "Source name is required")
    @Size(max = 100, message = "Source name cannot exceed 100 characters")
    private String sourceName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Min(value = 1, message = "Display order must be greater than 0")
    private Integer displayOrder;

    // ==========================================================
    // Status
    // ==========================================================

    @NotNull(message = "Source status is required")
    private CategoryStatus status;

    // ==========================================================
    // Constructors
    // ==========================================================

    public LeadSourceRequest() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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