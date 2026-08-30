package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : RecentNoteResponse
 *
 * Description :
 * Represents a recent note/activity associated with a lead
 * and displayed on the dashboard.
 *
 * Used for :
 * - Admin Dashboard
 * - Executive Dashboard
 * - Recent Notes section
 *
 * ==========================================================
 */
public class RecentNoteResponse {

    // ==========================================================
    // Note ID
    // ==========================================================

    /**
     * Unique identifier of the note.
     */
    private Long noteId;


    // ==========================================================
    // Lead ID
    // ==========================================================

    /**
     * ID of the lead associated with the note.
     */
    private Long leadId;


    // ==========================================================
    // Lead Name
    // ==========================================================

    /**
     * Name of the lead/customer associated with the note.
     */
    private String leadName;


    // ==========================================================
    // Note Content
    // ==========================================================

    /**
     * Content of the note.
     */
    private String note;


    // ==========================================================
    // Created By
    // ==========================================================

    /**
     * Name of the user who created the note.
     */
    private String createdBy;


    // ==========================================================
    // Created At
    // ==========================================================

    /**
     * Date and time when the note was created.
     */
    private String createdAt;


    // ==========================================================
    // Default Constructor
    // ==========================================================

    public RecentNoteResponse() {
    }


    // ==========================================================
    // Parameterized Constructor
    // ==========================================================

    public RecentNoteResponse(
            Long noteId,
            Long leadId,
            String leadName,
            String note,
            String createdBy,
            String createdAt
    ) {
        this.noteId = noteId;
        this.leadId = leadId;
        this.leadName = leadName;
        this.note = note;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
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


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}