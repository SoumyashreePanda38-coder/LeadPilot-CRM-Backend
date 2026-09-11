package com.leadpilot.crm.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizations")
public class Organization {

    // ==========================================================
    // PRIMARY KEY
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ==========================================================
    // ORGANIZATION NAME
    // ==========================================================

    private String organizationName;


    // ==========================================================
    // ORGANIZATION USERS
    //
    // One Organization can have:
    //
    // - Multiple ADMINs
    // - Multiple EXECUTIVEs
    //
    // ==========================================================

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL
    )
    private List<User> users = new ArrayList<>();


    // ==========================================================
    // AUDIT INFORMATION
    // ==========================================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    // ==========================================================
    // CONSTRUCTORS
    // ==========================================================

    public Organization() {
    }


    // ==========================================================
    // AUTOMATIC TIMESTAMPS
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
    // GETTERS AND SETTERS
    // ==========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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