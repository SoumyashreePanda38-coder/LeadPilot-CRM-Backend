package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : RecentLeadResponse
 *
 * Description :
 * Represents a recently created or recently updated lead
 * displayed on the dashboard.
 *
 * Used for :
 * - Admin Dashboard
 * - Executive Dashboard
 * - Recent Leads section
 *
 * ==========================================================
 */
public class RecentLeadResponse {

    // ==========================================================
    // Lead ID
    // ==========================================================

    /**
     * Unique identifier of the lead.
     */
    private Long leadId;


    // ==========================================================
    // Lead Name
    // ==========================================================

    /**
     * Name of the customer/lead.
     */
    private String leadName;


    // ==========================================================
    // Lead Status
    // ==========================================================

    /**
     * Current status of the lead.
     *
     * Example:
     * NEW, CONTACTED, QUALIFIED, FOLLOW_UP,
     * PROPOSAL_SENT, NEGOTIATION, WON, LOST, CLOSED
     */
    private String status;


    // ==========================================================
    // Lead Priority
    // ==========================================================

    /**
     * Current priority of the lead.
     *
     * Example:
     * HOT, WARM, COLD, NOT_A_CUSTOMER
     */
    private String priority;


    // ==========================================================
    // Assigned Executive
    // ==========================================================

    /**
     * Name of the executive assigned to the lead.
     */
    private String assignedExecutive;


    // ==========================================================
    // Created At
    // ==========================================================

    /**
     * Date and time when the lead was created.
     *
     * Stored as String in the response DTO so that the
     * frontend can directly consume the formatted value.
     */
    private String createdAt;


    // ==========================================================
    // Default Constructor
    // ==========================================================

    public RecentLeadResponse() {
    }


    // ==========================================================
    // Parameterized Constructor
    // ==========================================================

    public RecentLeadResponse(
            Long leadId,
            String leadName,
            String status,
            String priority,
            String assignedExecutive,
            String createdAt
    ) {
        this.leadId = leadId;
        this.leadName = leadName;
        this.status = status;
        this.priority = priority;
        this.assignedExecutive = assignedExecutive;
        this.createdAt = createdAt;
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


    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getAssignedExecutive() {
        return assignedExecutive;
    }

    public void setAssignedExecutive(String assignedExecutive) {
        this.assignedExecutive = assignedExecutive;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}