package com.leadpilot.crm.dto;

import java.time.LocalDateTime;

/**
 * ==========================================================
 * DTO : NoteResponse
 *
 * Description :
 * Used to send note information from the backend
 * to the frontend.
 *
 * Includes:
 *
 *  - Note information
 *  - Customer Lead information
 *  - Created By information
 *  - Updated By information
 *  - Deleted By information
 *  - Audit information
 *
 * ==========================================================
 */
public class NoteResponse {

    // ==========================================================
    // Primary Key
    // ==========================================================

    private Long noteId;

    // ==========================================================
    // Customer Lead Information
    // ==========================================================

    private Long leadId;

    private String leadName;

    // ==========================================================
    // Note Information
    // ==========================================================

    private String title;

    private String content;

    // ==========================================================
    // Note Flags
    // ==========================================================

    private boolean pinned;

    private boolean important;

    // ==========================================================
    // Created By Information
    // ==========================================================

    private Long createdById;

    private String createdByName;

    // ==========================================================
    // Updated By Information
    // ==========================================================

    private Long updatedById;

    private String updatedByName;

    // ==========================================================
    // Soft Delete Information
    // ==========================================================

    private boolean deleted;

    private LocalDateTime deletedAt;

    private Long deletedById;

    private String deletedByName;

    // ==========================================================
    // Audit Information
    // ==========================================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================================================
    // Constructor
    // ==========================================================

    public NoteResponse() {
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Long getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(Long deletedById) {
        this.deletedById = deletedById;
    }

    public String getDeletedByName() {
        return deletedByName;
    }

    public void setDeletedByName(String deletedByName) {
        this.deletedByName = deletedByName;
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