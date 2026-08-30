package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.LeadStatus;

import jakarta.validation.constraints.NotNull;

/**
 * ==========================================================
 * DTO : LeadStatusRequest
 *
 * Description :
 * Used to receive a lead status update from the frontend.
 * ==========================================================
 */
public class LeadStatusRequest {

    // ==========================================================
    // Lead Status
    // ==========================================================

    @NotNull(message = "Lead status is required")
    private LeadStatus leadStatus;

    // ==========================================================
    // Constructor
    // ==========================================================

    public LeadStatusRequest() {
    }

    // ==========================================================
    // Getter and Setter
    // ==========================================================

    public LeadStatus getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(LeadStatus leadStatus) {
        this.leadStatus = leadStatus;
    }
}