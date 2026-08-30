
package com.leadpilot.crm.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * ==========================================================
 * Entity : Note
 *
 * Description :
 * Stores notes created by Admin or CRM users/executives
 * against a customer lead.
 *
 * A lead can have unlimited notes.
 *
 * Supported operations:
 *
 * - Create Note
 * - View Note
 * - View All Notes
 * - View Notes By Lead
 * - Edit Note
 * - Delete Note
 * - Restore Deleted Note
 * - Pin / Unpin Note
 * - Mark Important / Unmark Important
 *
 * Notes are soft-deleted so that important CRM information
 * is not permanently lost accidentally.
 * ==========================================================
 */
@Entity
@Table(name = "notes")
public class Note {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long noteId;


    // ==========================================================
    // Customer Lead
    // ==========================================================

    /**
     * Lead to which this note belongs.
     *
     * One lead can have unlimited notes.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "lead_id",
        nullable = false
    )
    private CustomerLead customerLead;


    // ==========================================================
    // Note Information
    // ==========================================================

    /**
     * Short title of the note.
     *
     * Example:
     * "Customer requirement"
     * "Meeting discussion"
     * "Pricing concern"
     */
    @Column(
        name = "title",
        nullable = false,
        length = 200
    )
    private String title;


    /**
     * Complete note content.
     *
     * @Lob allows the CRM to store large amounts of text
     * without imposing an unnecessarily small character limit.
     */
    @Lob
    @Column(
        name = "content",
        nullable = false
    )
    private String content;


    // ==========================================================
    // Note Flags
    // ==========================================================

    /**
     * Pinned notes can be displayed at the top of a lead's
     * notes section.
     */
    @Column(
        name = "is_pinned",
        nullable = false
    )
    private boolean pinned = false;


    /**
     * Important notes can be highlighted by the frontend.
     */
    @Column(
        name = "is_important",
        nullable = false
    )
    private boolean important = false;


    // ==========================================================
    // Created By
    // ==========================================================

    /**
     * Admin or executive who created the note.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;


    // ==========================================================
    // Updated By
    // ==========================================================

    /**
     * Admin or executive who last modified the note.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;


    // ==========================================================
    // Soft Delete
    // ==========================================================

    /**
     * Indicates whether the note has been deleted.
     *
     * The note remains in the database and can be restored.
     */
    @Column(
        name = "is_deleted",
        nullable = false
    )
    private boolean deleted = false;


    /**
     * Date and time when the note was deleted.
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    /**
     * User who deleted the note.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by")
    private User deletedBy;


    // ==========================================================
    // Audit Information
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

    public Note() {
    }


    // ==========================================================
    // Automatically Set Audit Information
    // ==========================================================

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }


    @PreUpdate
    public void preUpdate() {

        this.updatedAt = LocalDateTime.now();
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


    public CustomerLead getCustomerLead() {
        return customerLead;
    }

    public void setCustomerLead(CustomerLead customerLead) {
        this.customerLead = customerLead;
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


    public User getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(User deletedBy) {
        this.deletedBy = deletedBy;
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