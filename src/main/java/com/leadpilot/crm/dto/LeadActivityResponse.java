package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.LeadActivityType;

/**
 * ==========================================================
 * DTO : LeadActivityResponse
 *
 * Description :
 * Used to return lead activity information to the frontend.
 * ==========================================================
 */
public class LeadActivityResponse {

    // ==========================================================
    // Primary Key
    // ==========================================================

    private Long activityId;

    // ==========================================================
    // Customer Lead
    // ==========================================================

    private Long leadId;
    private String leadName;

    // ==========================================================
    // Activity Type
    // ==========================================================

    private LeadActivityType activityType;

    // ==========================================================
    // Activity Description
    // ==========================================================

    private String description;

    // ==========================================================
    // User Who Performed The Activity
    // ==========================================================

    private Long performedById;
    private String performedByName;

    // ==========================================================
    // Activity Created At
    // ==========================================================

    private LocalDateTime createdAt;

    // ==========================================================
    // Constructor
    // ==========================================================

    public LeadActivityResponse() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
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

    public String getPerformedByName() {
        return performedByName;
    }

    public void setPerformedByName(String performedByName) {
        this.performedByName = performedByName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}