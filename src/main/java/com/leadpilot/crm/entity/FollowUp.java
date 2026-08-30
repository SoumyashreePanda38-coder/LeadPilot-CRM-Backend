package com.leadpilot.crm.entity;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * ==========================================================
 * Entity : FollowUp
 *
 * Description :
 * Represents a scheduled follow-up action for a customer lead.
 *
 * A follow-up records:
 *
 * - Which lead requires the action
 * - Which user/executive is responsible
 * - Type of follow-up
 * - Scheduled date and time
 * - Location when applicable
 * - Purpose/details of the follow-up
 * - Current follow-up status
 * - Completion time and outcome
 *
 * Examples:
 *
 * - Call customer tomorrow at 10:00 AM
 * - WhatsApp customer at 2:00 PM
 * - Meeting with customer at 4:00 PM
 * - Property/site visit at a specified location
 *
 * Reminder information is intentionally NOT stored here.
 * Reminder timing belongs to the Reminder entity.
 *
 * ==========================================================
 */
@Entity
@Table(name = "follow_ups")
public class FollowUp {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_up_id")
    private Long followUpId;


    // ==========================================================
    // Customer Lead
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "lead_id",
        nullable = false
    )
    private CustomerLead customerLead;


    // ==========================================================
    // Responsible Executive / User
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "assigned_user_id",
        nullable = false
    )
    private User assignedUser;


    // ==========================================================
    // Follow-Up Type
    // ==========================================================

    @Enumerated(EnumType.STRING)
    @Column(
        name = "follow_up_type",
        nullable = false,
        length = 30
    )
    private FollowUpType followUpType;


    // ==========================================================
    // Follow-Up Subject
    // ==========================================================

    @Column(
        name = "subject",
        nullable = false,
        length = 150
    )
    private String subject;


    // ==========================================================
    // Scheduled Date & Time
    // ==========================================================

    @Column(
        name = "scheduled_at",
        nullable = false
    )
    private LocalDateTime scheduledAt;


    // ==========================================================
    // Location
    //
    // Used mainly for meetings and visits.
    // Can remain null for calls, emails, WhatsApp, etc.
    // ==========================================================

    @Column(
        name = "location",
        length = 500
    )
    private String location;


    // ==========================================================
    // Follow-Up Details
    // ==========================================================

    @Column(
        name = "description",
        length = 1000
    )
    private String description;


    // ==========================================================
    // Follow-Up Status
    // ==========================================================

    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false,
        length = 30
    )
    private FollowUpStatus status;


    // ==========================================================
    // Completion Information
    // ==========================================================

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(
        name = "outcome",
        length = 1000
    )
    private String outcome;


    // ==========================================================
    // Audit Information
    // ==========================================================

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // ==========================================================
    // Optimistic Locking
    //
    // Prevents two users from overwriting the same
    // follow-up simultaneously.
    // ==========================================================

    @Version
    @Column(name = "version")
    private Long version;


    // ==========================================================
    // Constructor
    // ==========================================================

    public FollowUp() {
    }


    // ==========================================================
    // Automatically Set Audit Fields
    // ==========================================================

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.status == null) {
            this.status = FollowUpStatus.SCHEDULED;
        }
    }


    @PreUpdate
    public void preUpdate() {

        this.updatedAt = LocalDateTime.now();
    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(Long followUpId) {
        this.followUpId = followUpId;
    }


    public CustomerLead getCustomerLead() {
        return customerLead;
    }

    public void setCustomerLead(CustomerLead customerLead) {
        this.customerLead = customerLead;
    }


    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }


    public FollowUpType getFollowUpType() {
        return followUpType;
    }

    public void setFollowUpType(FollowUpType followUpType) {
        this.followUpType = followUpType;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public FollowUpStatus getStatus() {
        return status;
    }

    public void setStatus(FollowUpStatus status) {
        this.status = status;
    }


    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }


    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
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


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}