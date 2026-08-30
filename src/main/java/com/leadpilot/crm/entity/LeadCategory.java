package com.leadpilot.crm.entity;

import java.time.LocalDateTime;

// import java.util.List;

import com.leadpilot.crm.enums.CategoryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

/**
 * ==========================================================
 * Entity : LeadCategory
 *
 * Description :
 * Stores business categories under which customer
 * leads are organized.
 *
 * Examples:
 * • Real Estate
 * • Healthcare
 * • Education
 * • Automobile
 * • Retail
 * • Finance
 * • Manufacturing
 * • Hospitality
 * • IT Services
 *
 * Admin can:
 * • Add Category
 * • Edit Category
 * • Activate / Deactivate Category
 * • View Category Details
 * ==========================================================
 */

@Entity
@Table(name = "lead_categories")
public class LeadCategory {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    // ==========================================================
    // Category Information
    // ==========================================================

    @Column(name = "category_name", nullable = false, unique = true, length = 100)
    private String categoryName;

    @Column(length = 500)
    private String description;

    /**
     * Used for sorting categories in dropdowns.
     * Example:
     * 1 -> Real Estate
     * 2 -> Healthcare
     * 3 -> Education
     */
    @Column(name = "display_order")
    private Integer displayOrder;

    // ==========================================================
    // Category Status
    // ==========================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus status;

    // ==========================================================
    // Audit Information
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ==========================================================
    // Relationship with CustomerLead
    // Uncomment after CustomerLead entity is created
    // ==========================================================

    /*
    @OneToMany(mappedBy = "category")
    private List<CustomerLead> customerLeads;
    */

    // ==========================================================
    // Constructors
    // ==========================================================

    public LeadCategory() {
    }

    // ==========================================================
    // Automatically Set Audit Fields
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    Uncomment after CustomerLead entity is created

    public List<CustomerLead> getCustomerLeads() {
        return customerLeads;
    }

    public void setCustomerLeads(List<CustomerLead> customerLeads) {
        this.customerLeads = customerLeads;
    }
    ===========================================================
    */
}