package com.leadpilot.crm.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.Note;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.repository.NoteRepository;
import com.leadpilot.crm.service.NoteService;

/**
 * ==========================================================
 * Service Implementation : NoteServiceImpl
 *
 * Description :
 * Implements business operations for managing CRM notes
 * associated with customer leads.
 *
 * Supported operations:
 *
 *  - Create Note
 *  - Get All Notes
 *  - Get Note By ID
 *  - Get Notes By Lead
 *  - Get All Notes By Lead
 *  - Update Note
 *  - Soft Delete Note
 *  - Restore Deleted Note
 *  - Pin Note
 *  - Unpin Note
 *  - Mark Note Important
 *  - Mark Note Not Important
 *  - Get Pinned Notes By Lead
 *  - Get Important Notes By Lead
 *  - Get Notes By Created User
 *
 * Notes are soft-deleted and are not physically removed
 * from the database.
 *
 * ==========================================================
 */

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    // ==========================================================
    // Repository
    // ==========================================================

    @Autowired
    private NoteRepository noteRepository;


    // ==========================================================
    // CREATE NOTE
    // ==========================================================

    /**
     * Creates a new note.
     *
     * @param note note to create
     * @return saved note
     */
    @Override
    public Note createNote(Note note) {

        if (note == null) {
            throw new IllegalArgumentException(
                    "Note cannot be null"
            );
        }

        // ------------------------------------------------------
        // Validate Lead
        // ------------------------------------------------------

        if (note.getCustomerLead() == null) {
            throw new IllegalArgumentException(
                    "Customer lead is required"
            );
        }

        // ------------------------------------------------------
        // Validate Title
        // ------------------------------------------------------

        if (note.getTitle() == null
                || note.getTitle().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Note title is required"
            );
        }

        // ------------------------------------------------------
        // Validate Content
        // ------------------------------------------------------

        if (note.getContent() == null
                || note.getContent().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Note content is required"
            );
        }

        // ------------------------------------------------------
        // Clean title
        // ------------------------------------------------------

        note.setTitle(
                note.getTitle().trim()
        );

        // ------------------------------------------------------
        // New notes must be active
        // ------------------------------------------------------

        note.setDeleted(false);
        note.setDeletedAt(null);
        note.setDeletedBy(null);

        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return noteRepository.save(note);
    }


    // ==========================================================
    // GET ALL ACTIVE NOTES
    // ==========================================================

    /**
     * Gets all non-deleted notes.
     *
     * @return list of active notes
     */
    @Override
    public List<Note> getAllNotes() {

        return noteRepository
                .findByDeletedFalseOrderByCreatedAtDesc();
    }


    // ==========================================================
    // GET NOTE BY ID
    // ==========================================================

    /**
     * Gets a note by ID.
     *
     * Both active and deleted notes can be found.
     *
     * @param noteId note ID
     * @return note
     */
    @Override
    public Note getNoteById(Long noteId) {

        if (noteId == null) {
            throw new IllegalArgumentException(
                    "Note ID cannot be null"
            );
        }

        return noteRepository
                .findByNoteId(noteId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Note not found with ID: "
                                        + noteId
                        )
                );
    }


    // ==========================================================
    // GET NOTES BY LEAD
    // ==========================================================

    /**
     * Gets active notes belonging to a particular lead.
     *
     * @param customerLead customer lead
     * @return list of active notes
     */
    @Override
    public List<Note> getNotesByLead(
            CustomerLead customerLead) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        if (customerLead.getLeadId() == null) {
            throw new IllegalArgumentException(
                    "Customer lead ID cannot be null"
            );
        }

        return noteRepository
                .findActiveNotesByLeadId(
                        customerLead.getLeadId()
                );
    }


    // ==========================================================
    // GET ALL NOTES BY LEAD
    // INCLUDING DELETED
    // ==========================================================

    /**
     * Gets all notes belonging to a lead,
     * including soft-deleted notes.
     *
     * @param customerLead customer lead
     * @return list of all notes
     */
    @Override
    public List<Note> getAllNotesByLead(
            CustomerLead customerLead) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        if (customerLead.getLeadId() == null) {
            throw new IllegalArgumentException(
                    "Customer lead ID cannot be null"
            );
        }

        return noteRepository
                .findByLeadId(
                        customerLead.getLeadId()
                );
    }


    // ==========================================================
    // UPDATE NOTE
    // ==========================================================

    /**
     * Updates an existing note.
     *
     * The CustomerLead relationship is not changed here.
     *
     * @param noteId note ID
     * @param note updated note
     * @param updatedBy user performing update
     * @return updated note
     */
    @Override
    public Note updateNote(
            Long noteId,
            Note note,
            User updatedBy) {

        if (noteId == null) {
            throw new IllegalArgumentException(
                    "Note ID cannot be null"
            );
        }

        if (note == null) {
            throw new IllegalArgumentException(
                    "Note cannot be null"
            );
        }

        // ------------------------------------------------------
        // Find existing note
        // ------------------------------------------------------

        Note existingNote =
                noteRepository
                        .findByNoteId(noteId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Note not found with ID: "
                                                + noteId
                                )
                        );

        // ------------------------------------------------------
        // Do not update deleted notes
        // ------------------------------------------------------

        if (existingNote.isDeleted()) {

            throw new IllegalStateException(
                    "Deleted note cannot be updated. "
                            + "Restore the note first."
            );
        }

        // ------------------------------------------------------
        // Validate title
        // ------------------------------------------------------

        if (note.getTitle() == null
                || note.getTitle().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Note title is required"
            );
        }

        // ------------------------------------------------------
        // Validate content
        // ------------------------------------------------------

        if (note.getContent() == null
                || note.getContent().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Note content is required"
            );
        }

        // ------------------------------------------------------
        // Update basic information
        // ------------------------------------------------------

        existingNote.setTitle(
                note.getTitle().trim()
        );

        existingNote.setContent(
                note.getContent()
        );

        // ------------------------------------------------------
        // Updated By
        // ------------------------------------------------------

        if (updatedBy != null) {

            existingNote.setUpdatedBy(
                    updatedBy
            );
        }

        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return noteRepository.save(
                existingNote
        );
    }


    // ==========================================================
    // SOFT DELETE NOTE
    // ==========================================================

    /**
     * Soft-deletes a note.
     *
     * The note remains in the database.
     *
     * @param noteId note ID
     * @param deletedBy user performing deletion
     */
    @Override
    public void deleteNote(
            Long noteId,
            User deletedBy) {

        if (noteId == null) {
            throw new IllegalArgumentException(
                    "Note ID cannot be null"
            );
        }

        // ------------------------------------------------------
        // Find note
        // ------------------------------------------------------

        Note note =
                noteRepository
                        .findByNoteId(noteId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Note not found with ID: "
                                                + noteId
                                )
                        );

        // ------------------------------------------------------
        // Check if already deleted
        // ------------------------------------------------------

        if (note.isDeleted()) {

            throw new IllegalStateException(
                    "Note is already deleted"
            );
        }

        // ------------------------------------------------------
        // Soft delete
        // ------------------------------------------------------

        note.setDeleted(true);

        note.setDeletedAt(
                LocalDateTime.now()
        );

        note.setDeletedBy(
                deletedBy
        );

        // ------------------------------------------------------
        // Updated By
        // ------------------------------------------------------

        if (deletedBy != null) {

            note.setUpdatedBy(
                    deletedBy
            );
        }

        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        noteRepository.save(note);
    }


    // ==========================================================
    // RESTORE NOTE
    // ==========================================================

    /**
     * Restores a previously deleted note.
     *
     * @param noteId note ID
     * @return restored note
     */
    @Override
    public Note restoreNote(
            Long noteId) {

        if (noteId == null) {
            throw new IllegalArgumentException(
                    "Note ID cannot be null"
            );
        }

        // ------------------------------------------------------
        // Find note
        // ------------------------------------------------------

        Note note =
                noteRepository
                        .findByNoteId(noteId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Note not found with ID: "
                                                + noteId
                                )
                        );

        // ------------------------------------------------------
        // Check state
        // ------------------------------------------------------

        if (!note.isDeleted()) {

            throw new IllegalStateException(
                    "Note is not deleted"
            );
        }

        // ------------------------------------------------------
        // Restore
        // ------------------------------------------------------

        note.setDeleted(false);

        note.setDeletedAt(null);

        note.setDeletedBy(null);

        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return noteRepository.save(note);
    }


    // ==========================================================
    // PIN NOTE
    // ==========================================================

    /**
     * Pins an active note.
     *
     * @param noteId note ID
     * @return updated note
     */
    @Override
    public Note pinNote(
            Long noteId) {

        Note note = getActiveNote(noteId);

        note.setPinned(true);

        return noteRepository.save(note);
    }


    // ==========================================================
    // UNPIN NOTE
    // ==========================================================

    /**
     * Unpins an active note.
     *
     * @param noteId note ID
     * @return updated note
     */
    @Override
    public Note unpinNote(
            Long noteId) {

        Note note = getActiveNote(noteId);

        note.setPinned(false);

        return noteRepository.save(note);
    }


    // ==========================================================
    // MARK IMPORTANT
    // ==========================================================

    /**
     * Marks an active note as important.
     *
     * @param noteId note ID
     * @return updated note
     */
    @Override
    public Note markImportant(
            Long noteId) {

        Note note = getActiveNote(noteId);

        note.setImportant(true);

        return noteRepository.save(note);
    }


    // ==========================================================
    // MARK NOT IMPORTANT
    // ==========================================================

    /**
     * Removes the important flag from an active note.
     *
     * @param noteId note ID
     * @return updated note
     */
    @Override
    public Note markNotImportant(
            Long noteId) {

        Note note = getActiveNote(noteId);

        note.setImportant(false);

        return noteRepository.save(note);
    }


    // ==========================================================
    // GET PINNED NOTES BY LEAD
    // ==========================================================

    /**
     * Gets all active pinned notes belonging to a lead.
     *
     * @param customerLead customer lead
     * @return pinned notes
     */
    @Override
    public List<Note> getPinnedNotesByLead(
            CustomerLead customerLead) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        if (customerLead.getLeadId() == null) {
            throw new IllegalArgumentException(
                    "Customer lead ID cannot be null"
            );
        }

        return noteRepository
                .findPinnedNotesByLeadId(
                        customerLead.getLeadId()
                );
    }


    // ==========================================================
    // GET IMPORTANT NOTES BY LEAD
    // ==========================================================

    /**
     * Gets all active important notes belonging to a lead.
     *
     * @param customerLead customer lead
     * @return important notes
     */
    @Override
    public List<Note> getImportantNotesByLead(
            CustomerLead customerLead) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        if (customerLead.getLeadId() == null) {
            throw new IllegalArgumentException(
                    "Customer lead ID cannot be null"
            );
        }

        return noteRepository
                .findImportantNotesByLeadId(
                        customerLead.getLeadId()
                );
    }


    // ==========================================================
    // GET NOTES BY CREATED USER
    // ==========================================================

    /**
     * Gets all active notes created by a particular user.
     *
     * @param createdBy user who created the notes
     * @return notes created by the user
     */
    @Override
    public List<Note> getNotesByCreatedBy(
            User createdBy) {

        if (createdBy == null) {
            throw new IllegalArgumentException(
                    "Created by user cannot be null"
            );
        }

        if (createdBy.getId() == null) {
            throw new IllegalArgumentException(
                    "Created by user ID cannot be null"
            );
        }

        return noteRepository
                .findActiveNotesByCreatedById(
                        createdBy.getId()
                );
    }


    // ==========================================================
    // PRIVATE HELPER
    // ==========================================================

    /**
     * Gets an active note.
     *
     * Throws an exception if the note does not exist
     * or has already been soft-deleted.
     *
     * @param noteId note ID
     * @return active note
     */
    private Note getActiveNote(
            Long noteId) {

        if (noteId == null) {
            throw new IllegalArgumentException(
                    "Note ID cannot be null"
            );
        }

        Note note =
                noteRepository
                        .findByNoteId(noteId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Note not found with ID: "
                                                + noteId
                                )
                        );

        if (note.isDeleted()) {

            throw new IllegalStateException(
                    "Note is deleted. Restore it first."
            );
        }

        return note;
    }
}