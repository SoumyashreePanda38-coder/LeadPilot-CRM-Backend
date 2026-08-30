package com.leadpilot.crm.enums;

/**
 * ==========================================================
 * Enum : FollowUpStatus
 *
 * Description :
 * Defines the lifecycle status of a follow-up.
 *
 * A follow-up can move through these states during
 * its lifecycle.
 * ==========================================================
 */
public enum FollowUpStatus {

    // ==========================================================
    // Follow-Up Created / Scheduled
    // ==========================================================

    SCHEDULED,

    // ==========================================================
    // Follow-Up Successfully Completed
    // ==========================================================

    COMPLETED,

    // ==========================================================
    // Follow-Up Was Not Completed On Time
    // ==========================================================

    MISSED,

    // ==========================================================
    // Follow-Up Was Moved To Another Date/Time
    // ==========================================================

    RESCHEDULED,

    // ==========================================================
    // Follow-Up Was Cancelled
    // ==========================================================

    CANCELLED
}