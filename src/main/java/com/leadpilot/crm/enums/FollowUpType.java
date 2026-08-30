package com.leadpilot.crm.enums;

/**
 * ==========================================================
 * Enum : FollowUpType
 *
 * Description :
 * Defines the type of follow-up activity scheduled
 * for a customer lead.
 *
 * These values represent the primary action through
 * which an executive interacts with a lead.
 * ==========================================================
 */
public enum FollowUpType {

    // ==========================================================
    // Communication
    // ==========================================================

    CALL,

    EMAIL,

    SMS,

    WHATSAPP,

    // ==========================================================
    // Personal / Business Interaction
    // ==========================================================

    MEETING,

    VISIT,

    // ==========================================================
    // General Follow-Up
    // ==========================================================

    TASK
}