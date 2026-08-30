package com.leadpilot.crm.enums;

/**
 * ==========================================================
 * Enum : LeadActivityType
 *
 * Description :
 * Defines all possible activities/events that can occur
 * during the complete lifecycle of a customer lead.
 *
 * These values are stored in LeadActivity to maintain
 * the complete history/timeline of every lead.
 * ==========================================================
 */

public enum LeadActivityType {

    // ==========================================================
    // Lead Creation & Basic Updates
    // ==========================================================

    LEAD_CREATED,
    LEAD_UPDATED,

    // ==========================================================
    // Lead Assignment
    // ==========================================================

    LEAD_ASSIGNED,
    LEAD_REASSIGNED,
    LEAD_UNASSIGNED,

    // ==========================================================
    // Customer Communication
    // ==========================================================

    CALL,
    EMAIL,
    SMS,
    WHATSAPP,

    // ==========================================================
    // Meetings & Personal Interaction
    // ==========================================================

    MEETING_SCHEDULED,
    MEETING_COMPLETED,
    MEETING_CANCELLED,
    VISIT_SCHEDULED,
    VISIT_COMPLETED,
    VISIT_CANCELLED,

    // ==========================================================
    // Lead Status Changes
    // ==========================================================

    STATUS_CHANGED,

    // ==========================================================
    // Lead Priority Changes
    // ==========================================================

    PRIORITY_CHANGED,

    // ==========================================================
    // Lead Classification Changes
    // ==========================================================

    CATEGORY_CHANGED,
    SUBCATEGORY_CHANGED,
    SOURCE_CHANGED,

    // ==========================================================
    // Follow-Up Activities
    // ==========================================================

    FOLLOW_UP_CREATED,
    FOLLOW_UP_UPDATED,
    FOLLOW_UP_COMPLETED,
    FOLLOW_UP_RESCHEDULED,
    FOLLOW_UP_CANCELLED,
    FOLLOW_UP_MISSED,

    // ==========================================================
    // Notes
    // ==========================================================

    NOTE_ADDED,
    NOTE_UPDATED,
    NOTE_DELETED,

    // ==========================================================
    // Lead Conversion
    // ==========================================================

    LEAD_CONVERTED,

    // ==========================================================
    // Lead Closure
    // ==========================================================

    LEAD_CLOSED,
    LEAD_REOPENED,

    // ==========================================================
    // Customer/Lead Interest
    // ==========================================================

    INTERESTED,
    NOT_INTERESTED,

    // ==========================================================
    // Documents
    // ==========================================================

    DOCUMENT_ADDED,
    DOCUMENT_UPDATED,
    DOCUMENT_REMOVED,

    // ==========================================================
    // Import / Export
    // ==========================================================

    LEAD_IMPORTED,
    LEAD_EXPORTED,

    // ==========================================================
    // System / Administrative Activities
    // ==========================================================

    LEAD_ARCHIVED,
    LEAD_RESTORED
}