package com.leadpilot.crm.entity;

import java.time.LocalDateTime;

import com.leadpilot.crm.enums.LeadPriority;
import com.leadpilot.crm.enums.LeadStatus;

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

/**
 * ==========================================================
 * Entity : CustomerLead
 *
 * Description :
 * Stores customer/lead information in the LeadPilot CRM.
 *
 * A lead contains:
 *
 * - Personal information
 * - Contact information
 * - Address information
 * - Lead category
 * - Lead subcategory
 * - Lead source
 * - Lead status
 * - Lead priority
 * - Assigned executive
 * - Audit information
 *
 * ==========================================================
 */

@Entity
@Table(name = "customer_leads")
public class CustomerLead {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lead_id")
    private Long leadId;


    // ==========================================================
    // Customer / Lead Personal Information
    // ==========================================================

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "age")
    private Integer age;


    // ==========================================================
    // Contact Information
    // ==========================================================

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;


    // ==========================================================
    // Address Information
    // ==========================================================

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "pincode", length = 10)
    private String pincode;


    // ==========================================================
    // Lead Category
    //
    // Example:
    // Real Estate
    // Healthcare
    // Automobile
    // Education
    // Shopping
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "category_id",
        nullable = false
    )
    private LeadCategory leadCategory;


    // ==========================================================
    // Lead Subcategory
    //
    // Example:
    //
    // Real Estate
    //     -> Apartment
    //     -> Villa
    //     -> Bungalow
    //
    // Automobile
    //     -> Car
    //     -> Bike
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "subcategory_id",
        nullable = false
    )
    private LeadSubCategory leadSubCategory;


    // ==========================================================
    // Lead Source
    //
    // Example:
    // Website
    // WhatsApp
    // Facebook
    // Instagram
    // Referral
    // Advertisement
    // Walk-in
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "source_id",
        nullable = false
    )
    private LeadSource leadSource;


    // ==========================================================
    // Lead Status
    //
    // Controlled using LeadStatus enum.
    // ==========================================================

    @Enumerated(EnumType.STRING)
    @Column(
        name = "lead_status",
        nullable = false,
        length = 30
    )
    private LeadStatus leadStatus;


    // ==========================================================
    // Lead Priority
    //
    // Controlled using LeadPriority enum.
    //
    // HOT
    // WARM
    // COLD
    // NOT_A_CUSTOMER
    // ==========================================================

    @Enumerated(EnumType.STRING)
    @Column(
        name = "lead_priority",
        nullable = false,
        length = 30
    )
    private LeadPriority leadPriority;


    // ==========================================================
    // Assigned Executive
    //
    // A lead can be assigned to one employee/executive.
    //
    // User.java can later contain:
    //
    // @OneToMany(mappedBy = "assignedUser")
    // private List<CustomerLead> assignedLeads;
    //
    // We are NOT using CascadeType.ALL here.
    // ==========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;


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
    // Constructors
    // ==========================================================

    public CustomerLead() {
    }


    // ==========================================================
    // Automatically Set Audit Dates
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
    // Getters and Setters
    // ==========================================================

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }


    public LeadCategory getLeadCategory() {
        return leadCategory;
    }

    public void setLeadCategory(LeadCategory leadCategory) {
        this.leadCategory = leadCategory;
    }


    public LeadSubCategory getLeadSubCategory() {
        return leadSubCategory;
    }

    public void setLeadSubCategory(LeadSubCategory leadSubCategory) {
        this.leadSubCategory = leadSubCategory;
    }


    public LeadSource getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(LeadSource leadSource) {
        this.leadSource = leadSource;
    }


    public LeadStatus getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(LeadStatus leadStatus) {
        this.leadStatus = leadStatus;
    }


    public LeadPriority getLeadPriority() {
        return leadPriority;
    }

    public void setLeadPriority(LeadPriority leadPriority) {
        this.leadPriority = leadPriority;
    }


    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
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