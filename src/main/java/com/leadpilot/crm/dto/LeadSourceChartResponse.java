package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : LeadSourceChartResponse
 *
 * Description :
 * Represents lead count information for a particular
 * lead source.
 *
 * Used for :
 * - Admin Dashboard
 * - Executive Dashboard
 * - Lead Source Chart
 *
 * ==========================================================
 */
public class LeadSourceChartResponse {

    // ==========================================================
    // Lead Source
    // ==========================================================

    /**
     * Source from which the lead was generated.
     *
     * Example values:
     * - WEBSITE
     * - REFERRAL
     * - SOCIAL_MEDIA
     * - EMAIL
     * - PHONE
     * - WALK_IN
     */
    private String source;


    // ==========================================================
    // Lead Count
    // ==========================================================

    /**
     * Number of leads generated from this source.
     */
    private Long count;


    // ==========================================================
    // Default Constructor
    // ==========================================================

    public LeadSourceChartResponse() {
    }


    // ==========================================================
    // Parameterized Constructor
    // ==========================================================

    public LeadSourceChartResponse(
            String source,
            Long count
    ) {
        this.source = source;
        this.count = count;
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}