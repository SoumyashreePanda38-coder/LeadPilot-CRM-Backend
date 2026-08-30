package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : LeadStatusChartResponse
 *
 * Description :
 * Represents lead status information used for the dashboard
 * lead-status chart.
 *
 * The same DTO can be used by:
 *
 * - Admin Dashboard
 * - Executive Dashboard
 *
 * For ADMIN:
 * The count represents organization-wide leads.
 *
 * For EXECUTIVE:
 * The count represents only leads assigned to the
 * authenticated executive.
 *
 * This is a DTO only.
 * It is NOT a database entity.
 *
 * ==========================================================
 */
public class LeadStatusChartResponse {

    // ==========================================================
    // Lead Status
    // ==========================================================

    /**
     * Lead status.
     *
     * Example values:
     *
     * NEW
     * CONTACTED
     * QUALIFIED
     * FOLLOW_UP
     * CONVERTED
     * LOST
     * CLOSED
     */
    private String status;


    // ==========================================================
    // Lead Count
    // ==========================================================

    /**
     * Number of leads having this status.
     */
    private long count;


    // ==========================================================
    // Default Constructor
    // ==========================================================

    public LeadStatusChartResponse() {
    }


    // ==========================================================
    // Parameterized Constructor
    // ==========================================================

    public LeadStatusChartResponse(
            String status,
            long count
    ) {
        this.status = status;
        this.count = count;
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}