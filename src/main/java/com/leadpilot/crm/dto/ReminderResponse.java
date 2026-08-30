package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

/**
 * ==========================================================
 * DTO : ReminderResponse
 *
 * Description :
 * Used to send reminder information from the backend
 * to the frontend.
 *
 * Includes related Lead, Follow-Up and User information
 * required by dashboards, reminder lists and notifications.
 *
 * ==========================================================
 */
public class ReminderResponse {

    // ==========================================================
    // Primary Key
    // ==========================================================

    private Long reminderId;


    // ==========================================================
    // Customer Lead Information
    // ==========================================================

    private Long leadId;

    private String leadName;


    // ==========================================================
    // Follow-Up Information
    // ==========================================================

    private Long followUpId;

    private String followUpSubject;


    // ==========================================================
    // Assigned User Information
    // ==========================================================

    private Long assignedToId;

    private String assignedToName;


    // ==========================================================
    // Reminder Information
    // ==========================================================

    private String title;

    private String message;

    private LocalDateTime reminderAt;


    // ==========================================================
    // Reminder State
    // ==========================================================

    private boolean read;

    private boolean completed;

    private boolean dismissed;


    // ==========================================================
    // Notification Tracking
    // ==========================================================

    private boolean notificationSent;

    private LocalDateTime notificationSentAt;


    // ==========================================================
    // Completion Information
    // ==========================================================

    private LocalDateTime completedAt;


    // ==========================================================
    // Audit Information
    // ==========================================================

    private Long createdById;

    private String createdByName;

    private Long updatedById;

    private String updatedByName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    // ==========================================================
    // Constructor
    // ==========================================================

    public ReminderResponse() {
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getReminderId() {
        return reminderId;
    }

    public void setReminderId(Long reminderId) {
        this.reminderId = reminderId;
    }


    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }


    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }


    public Long getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(Long followUpId) {
        this.followUpId = followUpId;
    }


    public String getFollowUpSubject() {
        return followUpSubject;
    }

    public void setFollowUpSubject(String followUpSubject) {
        this.followUpSubject = followUpSubject;
    }


    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }


    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
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


    public boolean isNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }


    public LocalDateTime getNotificationSentAt() {
        return notificationSentAt;
    }

    public void setNotificationSentAt(
            LocalDateTime notificationSentAt) {

        this.notificationSentAt = notificationSentAt;
    }


    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }


    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }


    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }


    public Long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }


    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}