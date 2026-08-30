package com.leadpilot.crm.service;

import java.util.List;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.Note;
import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Service : NoteService
 *
 * Description :
 * Defines business operations for managing CRM notes
 * associated with customer leads.
 *
 * Supported operations:
 *
 * - Create Note
 * - Get All Notes
 * - Get Note By ID
 * - Get Notes By Lead
 * - Update Note
 * - Soft Delete Note
 * - Restore Deleted Note
 * - Pin Note
 * - Unpin Note
 * - Mark Note Important
 * - Mark Note Not Important
 *
 * Implementation:
 * NoteServiceImpl
 *
 * ==========================================================
 */
public interface NoteService {

    // ==========================================================
    // CREATE
    // ==========================================================

    /**
     * Create a new note.
     *
     * @param note note to create
     * @return saved note
     */
    Note createNote(Note note);


    // ==========================================================
    // READ
    // ==========================================================

    /**
     * Get all non-deleted notes.
     *
     * @return list of active notes
     */
    List<Note> getAllNotes();


    /**
     * Get a note by ID.
     *
     * @param noteId note ID
     * @return note
     */
    Note getNoteById(Long noteId);


    /**
     * Get all notes belonging to a particular lead.
     *
     * @param customerLead customer lead
     * @return list of notes
     */
    List<Note> getNotesByLead(CustomerLead customerLead);


    /**
     * Get all notes belonging to a lead including
     * deleted notes.
     *
     * @param customerLead customer lead
     * @return list of all notes
     */
    List<Note> getAllNotesByLead(CustomerLead customerLead);


    // ==========================================================
    // UPDATE
    // ==========================================================

    /**
     * Update an existing note.
     *
     * @param noteId note ID
     * @param note updated note information
     * @param updatedBy user performing the update
     * @return updated note
     */
    Note updateNote(
            Long noteId,
            Note note,
            User updatedBy
    );


    // ==========================================================
    // SOFT DELETE
    // ==========================================================

    /**
     * Soft-delete a note.
     *
     * The note is not physically removed from the database.
     *
     * @param noteId note ID
     * @param deletedBy user performing deletion
     */
    void deleteNote(
            Long noteId,
            User deletedBy
    );


    // ==========================================================
    // RESTORE
    // ==========================================================

    /**
     * Restore a previously deleted note.
     *
     * @param noteId note ID
     * @return restored note
     */
    Note restoreNote(Long noteId);


    // ==========================================================
    // PIN / UNPIN
    // ==========================================================

    /**
     * Pin a note.
     *
     * @param noteId note ID
     * @return updated note
     */
    Note pinNote(Long noteId);


    /**
     * Unpin a note.
     *
     * @param noteId note ID
     * @return updated note
     */
    Note unpinNote(Long noteId);


    // ==========================================================
    // IMPORTANT / NOT IMPORTANT
    // ==========================================================

    /**
     * Mark a note as important.
     *
     * @param noteId note ID
     * @return updated note
     */
    Note markImportant(Long noteId);


    /**
     * Mark a note as not important.
     *
     * @param noteId note ID
     * @return updated note
     */
    Note markNotImportant(Long noteId);


    // ==========================================================
    // FILTER OPERATIONS
    // ==========================================================

    /**
     * Get all pinned notes of a lead.
     *
     * @param customerLead customer lead
     * @return pinned notes
     */
    List<Note> getPinnedNotesByLead(
            CustomerLead customerLead
    );


    /**
     * Get all important notes of a lead.
     *
     * @param customerLead customer lead
     * @return important notes
     */
    List<Note> getImportantNotesByLead(
            CustomerLead customerLead
    );


    /**
     * Get notes created by a particular user.
     *
     * @param createdBy user who created the notes
     * @return notes
     */
    List<Note> getNotesByCreatedBy(
            User createdBy
    );
}