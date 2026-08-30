package com.leadpilot.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : NoteRequest
 *
 * Description :
 * Used to receive note information from the frontend.
 *
 * The frontend sends the Customer Lead ID instead of sending
 * the complete CustomerLead entity.
 *
 * Supported operations:
 *
 *  - Create Note
 *  - Update Note
 *
 * ==========================================================
 */
public class NoteRequest {

    // ==========================================================
    // Customer Lead
    // ==========================================================

    /**
     * ID of the customer lead to which this note belongs.
     */
    @NotNull(message = "Lead ID is required")
    private Long leadId;

    // ==========================================================
    // Note Information
    // ==========================================================

    /**
     * Short title of the note.
     *
     * Examples:
     *
     * - Customer requirement
     * - Meeting discussion
     * - Pricing concern
     */
    @NotBlank(message = "Note title is required")
    @Size(
        max = 200,
        message = "Note title cannot exceed 200 characters"
    )
    private String title;

    /**
     * Complete note content.
     */
    @NotBlank(message = "Note content is required")
    private String content;

    // ==========================================================
    // Note Flags
    // ==========================================================

    /**
     * Whether the note should be pinned.
     */
    private boolean pinned = false;

    /**
     * Whether the note should be marked as important.
     */
    private boolean important = false;

    // ==========================================================
    // Constructor
    // ==========================================================

    public NoteRequest() {
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
}