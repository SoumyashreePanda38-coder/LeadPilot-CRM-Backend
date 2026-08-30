package com.leadpilot.crm.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.leadpilot.crm.enums.CategoryStatus;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * ==========================================================
 * Entity : LeadSubCategory
 *
 * Description :
 * Represents the business types available under
 * a Lead Category.
 *
 * Examples:
 *
 * Category : Real Estate
 *     - Apartment
 *     - Villa
 *     - Plot
 *     - Commercial Building
 *
 * Category : Automobile
 *     - Car
 *     - Bike
 *     - Truck
 *
 * Category : Healthcare
 *     - Hospital
 *     - Clinic
 *     - Pharmacy
 *
 * Admin can:
 *     - Add
 *     - Edit
 *     - Activate / Deactivate
 *     - View
 *
 * ==========================================================
 */
@Entity
@Table(name = "lead_sub_categories")
public class LeadSubCategory {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    private Long subCategoryId;

    // ==========================================================
    // Parent Category
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "category_id",
        nullable = false
    )
    private LeadCategory leadCategory;

    // ==========================================================
    // Sub Category Information
    // ==========================================================

    @Column(
        name = "sub_category_name",
        nullable = false,
        length = 100
    )
    private String subCategoryName;

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
    // Audit Information
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

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
    // Relationship With CustomerLead
    //
    // CustomerLead contains:
    //
    // @ManyToOne
    // private LeadSubCategory leadSubCategory;
    //
    // Therefore mappedBy must exactly match:
    // "leadSubCategory"
    // ==========================================================

    @OneToMany(
        mappedBy = "leadSubCategory",
        fetch = FetchType.LAZY
    )
    private List<CustomerLead> customerLeads;

    // ==========================================================
    // Constructors
    // ==========================================================

    public LeadSubCategory() {
    }

    // ==========================================================
    // Audit
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

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public LeadCategory getLeadCategory() {
        return leadCategory;
    }

    public void setLeadCategory(LeadCategory leadCategory) {
        this.leadCategory = leadCategory;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
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

    public List<CustomerLead> getCustomerLeads() {
        return customerLeads;
    }

    public void setCustomerLeads(List<CustomerLead> customerLeads) {
        this.customerLeads = customerLeads;
    }
}