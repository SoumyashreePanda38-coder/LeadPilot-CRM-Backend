package com.leadpilot.crm.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * ==========================================================
 * Entity : Reminder
 *
 * Description :
 * Stores reminders that are shown to Admins and Executives
 * for important CRM activities.
 *
 * Typical uses:
 *
 * - Follow-up reminders
 * - Visit reminders
 * - Meeting reminders
 * - Call reminders
 * - Task reminders
 * - Upcoming customer interactions
 * - Important lead-related reminders
 *
 * A reminder can optionally be linked to a FollowUp and/or
 * CustomerLead.
 *
 * Multiple reminders can be created for the same lead or
 * follow-up.
 *
 * Examples:
 *
 * "Visit this customer tomorrow at 10:00 AM"
 * "Call customer regarding quotation"
 * "Follow up on pending requirement"
 *
 * ==========================================================
 */
@Entity
@Table(name = "reminders")
public class Reminder {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reminder_id")
    private Long reminderId;


    // ==========================================================
    // Related Customer Lead
    // ==========================================================

    /**
     * Lead associated with this reminder.
     *
     * This is optional because some reminders may be
     * administrative/system reminders.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private CustomerLead customerLead;


    // ==========================================================
    // Related Follow-Up
    // ==========================================================

    /**
     * Follow-up associated with this reminder.
     *
     * Example:
     *
     * FollowUp:
     * VISIT - 10 August 2026 - 10:00 AM
     *
     * Reminder:
     * "Visit reminder" - 10 August 2026 - 9:00 AM
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_up_id")
    private FollowUp followUp;


    // ==========================================================
    // User Who Should Receive The Reminder
    // ==========================================================

    /**
     * Admin or Executive who should receive this reminder.
     *
     * This allows reminders to be personalized for the
     * responsible CRM user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "assigned_to",
        nullable = false
    )
    private User assignedTo;


    // ==========================================================
    // Reminder Information
    // ==========================================================

    /**
     * Short title of the reminder.
     *
     * Examples:
     *
     * "Customer Visit"
     * "Follow-Up Call"
     * "Quotation Follow-Up"
     */
    @Column(
        name = "title",
        nullable = false,
        length = 200
    )
    private String title;


    /**
     * Detailed reminder message.
     *
     * Example:
     *
     * "Visit ABC customer regarding the commercial
     * property requirement."
     */
    @Column(
        name = "message",
        length = 1000
    )
    private String message;


    // ==========================================================
    // Reminder Date and Time
    // ==========================================================

    /**
     * Exact date and time when the reminder should become
     * due.
     */
    @Column(
        name = "reminder_at",
        nullable = false
    )
    private LocalDateTime reminderAt;


    // ==========================================================
    // Reminder State
    // ==========================================================

    /**
     * Indicates whether the reminder has been viewed by
     * the assigned user.
     */
    @Column(
        name = "is_read",
        nullable = false
    )
    private boolean read = false;


    /**
     * Indicates whether the reminder has been completed.
     *
     * Example:
     *
     * A visit reminder is completed after the executive
     * completes the visit.
     */
    @Column(
        name = "is_completed",
        nullable = false
    )
    private boolean completed = false;


    /**
     * Indicates whether the reminder has been dismissed.
     */
    @Column(
        name = "is_dismissed",
        nullable = false
    )
    private boolean dismissed = false;


    // ==========================================================
    // Notification Tracking
    // ==========================================================

    /**
     * Indicates whether the system has already generated
     * or sent the notification for this reminder.
     *
     * This helps prevent the same reminder from being
     * repeatedly sent.
     */
    @Column(
        name = "notification_sent",
        nullable = false
    )
    private boolean notificationSent = false;


    /**
     * Date and time when the reminder notification was sent.
     */
    @Column(name = "notification_sent_at")
    private LocalDateTime notificationSentAt;


    // ==========================================================
    // Completion Information
    // ==========================================================

    /**
     * Date and time when the reminder was completed.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;


    // ==========================================================
    // Audit Information
    // ==========================================================

    /**
     * Admin or Executive who created the reminder.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;


    /**
     * User who last modified the reminder.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;


    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // ==========================================================
    // Constructor
    // ==========================================================

    public Reminder() {
    }


    // ==========================================================
    // Automatically Set Audit Information
    // ==========================================================

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }


    @PreUpdate
    public void preUpdate() {

        this.updatedAt = LocalDateTime.now();
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


    public CustomerLead getCustomerLead() {
        return customerLead;
    }

    public void setCustomerLead(CustomerLead customerLead) {
        this.customerLead = customerLead;
    }


    public FollowUp getFollowUp() {
        return followUp;
    }

    public void setFollowUp(FollowUp followUp) {
        this.followUp = followUp;
    }


    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
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

    public void setCompletedAt(
            LocalDateTime completedAt) {

        this.completedAt = completedAt;
    }


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }


    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
} 