package com.leadpilot.crm.enums;

/**
 * ==========================================================
 * Enum : CategoryStatus
 *
 * Description :
 * Represents the current status of a Lead Category.
 * Categories are generally deactivated instead of deleted
 * to preserve historical lead records.
 * ==========================================================
 */
public enum CategoryStatus {

    /**
     * Category is active and can be assigned to leads.
     */
    ACTIVE,

    /**
     * Category is inactive and cannot be assigned
     * to new leads.
     */
    INACTIVE

}