package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : LeadPriorityChartResponse
 *
 * Description :
 * Represents lead priority information used for the
 * dashboard lead-priority chart.
 *
 * This DTO can be used by:
 *
 * - Admin Dashboard
 * - Executive Dashboard
 *
 * ADMIN:
 *     Represents organization-wide lead priority counts.
 *
 * EXECUTIVE:
 *     Represents lead priority counts for the authenticated
 *     executive's assigned leads.
 *
 * This is a DTO only.
 * It is NOT a database entity.
 *
 * ==========================================================
 */
public class LeadPriorityChartResponse {

    // ==========================================================
    // Lead Priority
    // ==========================================================

    /**
     * Lead priority.
     *
     * Example values:
     *
     * HOT
     * WARM
     * COLD
     * NOT_A_CUSTOMER
     */
    private String priority;


    // ==========================================================
    // Lead Count
    // ==========================================================

    /**
     * Number of leads having this priority.
     */
    private long count;


    // ==========================================================
    // Default Constructor
    // ==========================================================

    public LeadPriorityChartResponse() {
    }


    // ==========================================================
    // Parameterized Constructor
    // ==========================================================

    public LeadPriorityChartResponse(
            String priority,
            long count
    ) {
        this.priority = priority;
        this.count = count;
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}