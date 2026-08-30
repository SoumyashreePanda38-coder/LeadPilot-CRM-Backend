package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : OverdueFollowUpResponse
 *
 * Description :
 * Represents a follow-up that was scheduled in the past
 * but has not yet been completed.
 *
 * Used for :
 * - Admin Dashboard
 * - Executive Dashboard
 * - Overdue Follow-Ups section
 *
 * ==========================================================
 */
public class OverdueFollowUpResponse {

    // ==========================================================
    // Follow-Up ID
    // ==========================================================

    /**
     * Unique identifier of the follow-up.
     */
    private Long followUpId;


    // ==========================================================
    // Lead ID
    // ==========================================================

    /**
     * ID of the lead associated with the follow-up.
     */
    private Long leadId;


    // ==========================================================
    // Lead Name
    // ==========================================================

    /**
     * Name of the lead/customer associated with the follow-up.
     */
    private String leadName;


    // ==========================================================
    // Follow-Up Type
    // ==========================================================

    /**
     * Type of follow-up.
     *
     * Example:
     * CALL, EMAIL, MEETING, VISIT
     */
    private String followUpType;


    // ==========================================================
    // Scheduled At
    // ==========================================================

    /**
     * Date and time when the follow-up was originally scheduled.
     */
    private String scheduledAt;


    // ==========================================================
    // Follow-Up Status
    // ==========================================================

    /**
     * Current status of the follow-up.
     *
     * Example:
     * SCHEDULED, MISSED, RESCHEDULED
     */
    private String status;


    // ==========================================================
    // Assigned Executive
    // ==========================================================

    /**
     * Name of the executive assigned to the follow-up.
     */
    private String assignedExecutive;


    // ==========================================================
    // Default Constructor
    // ==========================================================

    public OverdueFollowUpResponse() {
    }


    // ==========================================================
    // Parameterized Constructor
    // ==========================================================

    public OverdueFollowUpResponse(
            Long followUpId,
            Long leadId,
            String leadName,
            String followUpType,
            String scheduledAt,
            String status,
            String assignedExecutive
    ) {
        this.followUpId = followUpId;
        this.leadId = leadId;
        this.leadName = leadName;
        this.followUpType = followUpType;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.assignedExecutive = assignedExecutive;
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


    public String getFollowUpType() {
        return followUpType;
    }

    public void setFollowUpType(String followUpType) {
        this.followUpType = followUpType;
    }


    public String getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(String scheduledAt) {
        this.scheduledAt = scheduledAt;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getAssignedExecutive() {
        return assignedExecutive;
    }

    public void setAssignedExecutive(String assignedExecutive) {
        this.assignedExecutive = assignedExecutive;
    }
}