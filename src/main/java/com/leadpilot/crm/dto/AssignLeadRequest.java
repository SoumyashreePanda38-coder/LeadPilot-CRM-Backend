package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : AssignLeadRequest
 *
 * Description :
 * Used to assign a customer lead to an employee/executive.
 *
 * ==========================================================
 */
public class AssignLeadRequest {

    // ==========================================================
    // Lead To Be Assigned
    // ==========================================================

    private Long leadId;

    // ==========================================================
    // User / Executive To Whom Lead Is Assigned
    // ==========================================================

    private Long assignedUserId;

    // ==========================================================
    // Constructors
    // ==========================================================

    public AssignLeadRequest() {
    }

    // ==========================================================
    // Getters & Setters
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
}