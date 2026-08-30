package com.leadpilot.crm.entity;

import java.time.LocalDateTime;

// import java.util.List;

import com.leadpilot.crm.enums.CategoryStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "lead_sources")
public class LeadSource {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lead_source_id")
    private Long leadSourceId;

    // ==========================================================
    // Source Information
    // ==========================================================

    @Column(name = "source_name",
            nullable = false,
            unique = true,
            length = 100)
    private String sourceName;

    @Column(length = 500)
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder;

    // ==========================================================
    // Status
    // ==========================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus status;

    // ==========================================================
    // Audit Fields
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "created_at",
            nullable = false,
            updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
    ===========================================================
    Uncomment after CustomerLead entity is created

    @OneToMany(mappedBy = "leadSource")
    private List<CustomerLead> customerLeads;

    ===========================================================
    */

    // ==========================================================
    // Constructors
    // ==========================================================

    public LeadSource() {
    }

    // ==========================================================
    // Audit Methods
    // ==========================================================

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================================
    // Getters & Setters
    // ==========================================================

    public Long getLeadSourceId() {
        return leadSourceId;
    }

    public void setLeadSourceId(Long leadSourceId) {
        this.leadSourceId = leadSourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
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

    /*
    ===========================================================

    public List<CustomerLead> getCustomerLeads() {
        return customerLeads;
    }

    public void setCustomerLeads(List<CustomerLead> customerLeads) {
        this.customerLeads = customerLeads;
    }

    ===========================================================
    */
}