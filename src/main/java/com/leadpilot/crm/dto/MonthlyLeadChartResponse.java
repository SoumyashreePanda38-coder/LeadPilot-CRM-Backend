package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : MonthlyLeadChartResponse
 *
 * Description :
 * Represents the number of leads generated during a
 * particular month.
 *
 * Used for :
 * - Admin Dashboard
 * - Executive Dashboard
 * - Monthly Lead Chart
 *
 * ==========================================================
 */
public class MonthlyLeadChartResponse {

    // ==========================================================
    // Month
    // ==========================================================

    /**
     * Month represented in the chart.
     *
     * Example:
     * January
     * February
     * March
     *
     * Or a formatted value such as:
     * 2026-01
     */
    private String month;


    // ==========================================================
    // Lead Count
    // ==========================================================

    /**
     * Number of leads generated during the month.
     */
    private Long count;


    // ==========================================================
    // Default Constructor
    // ==========================================================

    public MonthlyLeadChartResponse() {
    }


    // ==========================================================
    // Parameterized Constructor
    // ==========================================================

    public MonthlyLeadChartResponse(
            String month,
            Long count
    ) {
        this.month = month;
        this.count = count;
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}