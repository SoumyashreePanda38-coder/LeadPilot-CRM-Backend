package com.leadpilot.crm.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.leadpilot.crm.enums.Role;
import com.leadpilot.crm.enums.UserStatus;

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
 * Entity : User
 *
 * Description :
 * Stores all employee information including
 * Admin and Executive users.
 *
 * This entity contains:
 *
 * - Employee information
 * - Role and status
 * - Organization information
 * - Profile information
 * - Login tracking
 * - Creator / updater audit relationships
 * - Created / updated timestamps
 * - Assigned lead relationship
 *
 * ==========================================================
 */

@Entity
@Table(name = "users")
public class User {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ==========================================================
    // Employee Information
    // ==========================================================

    @Column(
        name = "employee_id",
        nullable = false,
        unique = true,
        length = 20
    )
    private String employeeId;

    @Column(
        name = "full_name",
        nullable = false,
        length = 100
    )
    private String fullName;

    @Column(
        nullable = false,
        unique = true,
        length = 50
    )
    private String username;

    /**
     * BCrypt encrypted password.
     */
    @Column(
        nullable = false,
        length = 255
    )
    private String password;

    @Column(
        nullable = false,
        unique = true,
        length = 100
    )
    private String email;

    @Column(
        name = "phone_number",
        nullable = false,
        length = 15
    )
    private String phoneNumber;


    // ==========================================================
    // User Role & Status
    // ==========================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 100)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;


    // ==========================================================
    // Profile
    // ==========================================================

    @Column(
        name = "profile_image",
        length = 500
    )
    private String profileImage;


    // ==========================================================
    // Organization Information
    // ==========================================================

    @Column(length = 100)
    private String department;


    // ==========================================================
    // Login Tracking
    // ==========================================================

    @Column(name = "last_login")
    private LocalDateTime lastLogin;


    // ==========================================================
    // Audit Relationships
    // ==========================================================

    /**
     * Admin/user who created this employee record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    /**
     * Admin/user who last modified this employee record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;


    // ==========================================================
    // Assigned Leads
    // ==========================================================

    /**
     * All leads currently assigned to this user.
     *
     * The relationship is mapped by the "assignedUser"
     * field inside CustomerLead.
     *
     * No CascadeType.ALL is used intentionally.
     *
     * CustomerLead is responsible for its own lifecycle.
     */
    @OneToMany(
        mappedBy = "assignedUser",
        fetch = FetchType.LAZY
    )
    private List<CustomerLead> assignedLeads = new ArrayList<>();


    // ==========================================================
    // Audit Fields
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
    // Constructors
    // ==========================================================

    public User() {
    }


    // ==========================================================
    // Automatically Set Date & Time
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }


    // ==========================================================
    // Created By / Updated By
    // ==========================================================

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


    // ==========================================================
    // Assigned Leads
    // ==========================================================

    public List<CustomerLead> getAssignedLeads() {
        return assignedLeads;
    }

    public void setAssignedLeads(
            List<CustomerLead> assignedLeads
    ) {
        this.assignedLeads = assignedLeads;
    }


    // ==========================================================
    // Audit Timestamps
    // ==========================================================

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