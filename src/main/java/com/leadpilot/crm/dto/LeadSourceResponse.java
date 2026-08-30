package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * DTO : LeadSourceResponse
 *
 * Description :
 * Used to send lead source information to the frontend.
 * Includes audit information.
 * ==========================================================
 */
public class LeadSourceResponse {

    // ==========================================================
    // Primary Key
    // ==========================================================

    private Long leadSourceId;

    // ==========================================================
    // Source Information
    // ==========================================================

    private String sourceName;

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

    public LeadSourceResponse() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

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