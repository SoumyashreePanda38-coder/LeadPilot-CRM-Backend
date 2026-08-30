package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : UpcomingVisitResponse
 *
 * Description :
 * Represents an upcoming visit scheduled for a lead/customer
 * and displayed on the dashboard.
 *
 * Used for :
 * - Admin Dashboard
 * - Executive Dashboard
 * - Upcoming Visits section
 *
 * ==========================================================
 */
public class UpcomingVisitResponse {

    // ==========================================================
    // Visit ID
    // ==========================================================

    /**
     * Unique identifier of the visit.
     */
    private Long visitId;


    // ==========================================================
    // Lead ID
    // ==========================================================

    /**
     * ID of the lead associated with the visit.
     */
    private Long leadId;


    // ==========================================================
    // Lead Name
    // ==========================================================

    /**
     * Name of the lead/customer associated with the visit.
     */
    private String leadName;


    // ==========================================================
    // Visit Date
    // ==========================================================

    /**
     * Date and time of the upcoming visit.
     */
    private String visitDate;


    // ==========================================================
    // Visit Location
    // ==========================================================

    /**
     * Location where the visit will take place.
     */
    private String location;


    // ==========================================================
    // Assigned Executive
    // ==========================================================

    /**
     * Name of the executive assigned to the visit.
     */
    private String assignedExecutive;


    // ==========================================================
    // Visit Status
    // ==========================================================

    /**
     * Current status of the visit.
     *
     * Example:
     * SCHEDULED, COMPLETED, CANCELLED
     */
    private String status;


    // ==========================================================
    // Default Constructor
    // ==========================================================

    public UpcomingVisitResponse() {
    }


    // ==========================================================
    // Parameterized Constructor
    // ==========================================================

    public UpcomingVisitResponse(
            Long visitId,
            Long leadId,
            String leadName,
            String visitDate,
            String location,
            String assignedExecutive,
            String status
    ) {
        this.visitId = visitId;
        this.leadId = leadId;
        this.leadName = leadName;
        this.visitDate = visitDate;
        this.location = location;
        this.assignedExecutive = assignedExecutive;
        this.status = status;
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
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


    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getAssignedExecutive() {
        return assignedExecutive;
    }

    public void setAssignedExecutive(String assignedExecutive) {
        this.assignedExecutive = assignedExecutive;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}