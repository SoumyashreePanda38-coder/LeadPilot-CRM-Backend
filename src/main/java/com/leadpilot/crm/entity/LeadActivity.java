package com.leadpilot.crm.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leadpilot.crm.enums.LeadActivityType;

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
import jakarta.persistence.Table;

/**
 * ==========================================================
 * Entity : LeadActivity
 *
 * Description :
 * Stores the complete activity history/timeline of a
 * customer lead.
 *
 * Activities may include:
 *
 * - Lead creation and updates
 * - Lead assignment/reassignment
 * - Calls, emails, SMS and WhatsApp
 * - Meetings and visits
 * - Status and priority changes
 * - Category/subcategory/source changes
 * - Follow-ups
 * - Notes
 * - Lead conversion
 * - Lead closure/reopening
 * - Documents
 * - Import/export
 * - Administrative activities
 * ==========================================================
 */

@Entity
@Table(name = "lead_activities")
public class LeadActivity {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;


    // ==========================================================
    // Customer Lead
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "lead_id",
        nullable = false
    )
    @JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
    })
    private CustomerLead customerLead;

    // ==========================================================
    // Activity Type
    // ==========================================================

    @Enumerated(EnumType.STRING)
    @Column(
        name = "activity_type",
        nullable = false,
        length = 50
    )
    private LeadActivityType activityType;


    // ==========================================================
    // Activity Description
    // ==========================================================

    @Column(
        name = "description",
        length = 1000
    )
    private String description;


    // ==========================================================
    // User Who Performed The Activity
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    @JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
    })
    private User performedBy;

    // ==========================================================
    // Activity Created At
    // ==========================================================

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;


    // ==========================================================
    // Constructor
    // ==========================================================

    public LeadActivity() {
    }


    // ==========================================================
    // Automatically Set Created Date
    // ==========================================================

    @PrePersist
    public void prePersist() {

        this.createdAt = LocalDateTime.now();

    }


    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }


    public CustomerLead getCustomerLead() {
        return customerLead;
    }

    public void setCustomerLead(CustomerLead customerLead) {
        this.customerLead = customerLead;
    }


    public LeadActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(LeadActivityType activityType) {
        this.activityType = activityType;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public User getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}