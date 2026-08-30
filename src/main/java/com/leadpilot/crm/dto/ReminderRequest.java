package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : ReminderRequest
 *
 * Description :
 * Used to receive reminder information from the frontend.
 *
 * The frontend sends IDs for the related Lead, Follow-Up
 * and assigned User instead of sending complete entities.
 *
 * Supported operations:
 *
 * - Create Reminder
 * - Update Reminder
 *
 * ==========================================================
 */
public class ReminderRequest {

    // ==========================================================
    // Related Customer Lead
    // ==========================================================

    /**
     * ID of the customer lead associated with the reminder.
     *
     * This field is optional because a reminder may be
     * an administrative/system reminder.
     */
    private Long leadId;


    // ==========================================================
    // Related Follow-Up
    // ==========================================================

    /**
     * ID of the follow-up associated with this reminder.
     *
     * This field is optional.
     */
    private Long followUpId;


    // ==========================================================
    // Assigned User
    // ==========================================================

    /**
     * ID of the user who should receive the reminder.
     */
    @NotNull(message = "Assigned user ID is required")
    private Long assignedToId;


    // ==========================================================
    // Reminder Information
    // ==========================================================

    /**
     * Short title of the reminder.
     *
     * Examples:
     *
     * - Customer Visit
     * - Follow-Up Call
     * - Quotation Follow-Up
     */
    @NotBlank(message = "Reminder title is required")
    @Size(
        max = 200,
        message = "Reminder title cannot exceed 200 characters"
    )
    private String title;


    /**
     * Detailed reminder message.
     */
    @Size(
        max = 1000,
        message = "Reminder message cannot exceed 1000 characters"
    )
    private String message;


    // ==========================================================
    // Reminder Date & Time
    // ==========================================================

    /**
     * Date and time when the reminder should become due.
     */
    @NotNull(message = "Reminder date and time is required")
    private LocalDateTime reminderAt;


    // ==========================================================
    // Reminder State
    // ==========================================================

    /**
     * Whether the reminder has been read.
     *
     * Normally controlled by the backend.
     */
    private boolean read = false;


    /**
     * Whether the reminder has been completed.
     *
     * Normally controlled by the backend.
     */
    private boolean completed = false;


    /**
     * Whether the reminder has been dismissed.
     *
     * Normally controlled by the backend.
     */
    private boolean dismissed = false;


    // ==========================================================
    // Constructor
    // ==========================================================

    public ReminderRequest() {
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }


    public Long getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(Long followUpId) {
        this.followUpId = followUpId;
    }


    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public LocalDateTime getReminderAt() {
        return reminderAt;
    }

    public void setReminderAt(LocalDateTime reminderAt) {
        this.reminderAt = reminderAt;
    }


    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }
}