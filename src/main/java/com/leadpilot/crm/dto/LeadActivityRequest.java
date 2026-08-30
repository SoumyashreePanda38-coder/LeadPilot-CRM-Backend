package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.LeadActivityType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : LeadActivityRequest
 *
 * Description :
 * Used to receive lead activity information from the frontend.
 * ==========================================================
 */
public class LeadActivityRequest {

    // ==========================================================
    // Customer Lead
    // ==========================================================

    @NotNull(message = "Lead is required")
    private Long leadId;

    // ==========================================================
    // Activity Type
    // ==========================================================

    @NotNull(message = "Activity type is required")
    private LeadActivityType activityType;

    // ==========================================================
    // Activity Description
    // ==========================================================

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    // ==========================================================
    // User Who Performed The Activity
    // ==========================================================

    private Long performedById;

    // ==========================================================
    // Constructor
    // ==========================================================

    public LeadActivityRequest() {
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

    public LeadActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(LeadActivityType activityType) {
        this.activityType = activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPerformedById() {
        return performedById;
    }

    public void setPerformedById(Long performedById) {
        this.performedById = performedById;
    }
}