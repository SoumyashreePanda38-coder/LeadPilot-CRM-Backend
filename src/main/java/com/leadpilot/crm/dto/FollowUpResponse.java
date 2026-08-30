package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;

/**
 * ==========================================================
 * DTO : FollowUpResponse
 *
 * Description :
 * Used to send follow-up information from the backend
 * to the frontend.
 *
 * Includes related lead and assigned-user information
 * required by dashboards, follow-up lists and calendars.
 * ==========================================================
 */
public class FollowUpResponse {

    // ==========================================================
    // Primary Key
    // ==========================================================

    private Long followUpId;

    // ==========================================================
    // Customer Lead Information
    // ==========================================================

    private Long leadId;

    private String leadName;

    // ==========================================================
    // Assigned User / Executive Information
    // ==========================================================

    private Long assignedUserId;

    private String assignedUserName;

    // ==========================================================
    // Follow-Up Information
    // ==========================================================

    private FollowUpType followUpType;

    private String subject;

    private LocalDateTime scheduledAt;

    private String location;

    private String description;

    // ==========================================================
    // Status
    // ==========================================================

    private FollowUpStatus status;

    // ==========================================================
    // Completion Information
    // ==========================================================

    private LocalDateTime completedAt;

    private String outcome;

    // ==========================================================
    // Audit Information
    // ==========================================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================================================
    // Optimistic Locking
    // ==========================================================

    private Long version;

    // ==========================================================
    // Constructor
    // ==========================================================

    public FollowUpResponse() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(Long followUpId) {
        this.followUpId = followUpId;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}