package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;

/**
 * ==========================================================
 * DTO : FollowUpRequest
 *
 * Description :
 * Used to receive follow-up data from the frontend.
 *
 * The frontend sends IDs for the related Lead and User
 * instead of sending complete entity objects.
 * ==========================================================
 */
public class FollowUpRequest {

    // ==========================================================
    // Customer Lead
    // ==========================================================

    private Long leadId;

    // ==========================================================
    // Responsible Executive / User
    // ==========================================================

    private Long assignedUserId;

    // ==========================================================
    // Follow-Up Type
    // ==========================================================

    private FollowUpType followUpType;

    // ==========================================================
    // Follow-Up Subject
    // ==========================================================

    private String subject;

    // ==========================================================
    // Scheduled Date & Time
    // ==========================================================

    private LocalDateTime scheduledAt;

    // ==========================================================
    // Location
    //
    // Mainly used for meetings and visits.
    // ==========================================================

    private String location;

    // ==========================================================
    // Follow-Up Details
    // ==========================================================

    private String description;

    // ==========================================================
    // Status
    //
    // Normally assigned by backend as SCHEDULED during
    // creation, but included for update operations.
    // ==========================================================

    private FollowUpStatus status;

    // ==========================================================
    // Completion Information
    // ==========================================================

    private LocalDateTime completedAt;

    private String outcome;

    // ==========================================================
    // Constructor
    // ==========================================================

    public FollowUpRequest() {
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

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public FollowUpType getFollowUpType() {
        return followUpType;
    }

    public void setFollowUpType(FollowUpType followUpType) {
        this.followUpType = followUpType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FollowUpStatus getStatus() {
        return status;
    }

    public void setStatus(FollowUpStatus status) {
        this.status = status;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}