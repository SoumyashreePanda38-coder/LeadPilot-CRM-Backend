package com.leadpilot.crm.mapper;

import com.leadpilot.crm.dto.NoteRequest;
import com.leadpilot.crm.dto.NoteResponse;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.Note;
import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Mapper : NoteMapper
 *
 * Description :
 * Converts Note entities to DTOs and DTOs to entities.
 *
 * Responsibilities:
 *
 *  1. Note -> NoteResponse
 *  2. NoteRequest -> Note
 *  3. Update existing Note from NoteRequest
 *
 * Important:
 *
 *  - This mapper does NOT perform database operations.
 *  - CustomerLead and User relationships are resolved
 *    separately by the service layer.
 *  - createdBy, updatedBy and deletedBy are intentionally
 *    not assigned from NoteRequest.
 *
 * ==========================================================
 */
public class NoteMapper {

    // ==========================================================
    // Private Constructor
    // ==========================================================

    private NoteMapper() {
        // Utility mapper class
    }


    // ==========================================================
    // Entity -> Response DTO
    // ==========================================================

    /**
     * Converts a Note entity into NoteResponse DTO.
     *
     * @param note Note entity
     * @return NoteResponse DTO
     */
    public static NoteResponse toResponse(Note note) {

        if (note == null) {
            return null;
        }

        NoteResponse response = new NoteResponse();


        // ======================================================
        // Primary Key
        // ======================================================

        response.setNoteId(
                note.getNoteId()
        );


        // ======================================================
        // Customer Lead Information
        // ======================================================

        CustomerLead customerLead =
                note.getCustomerLead();

        if (customerLead != null) {

            response.setLeadId(
                    customerLead.getLeadId()
            );

            /*
             * Assumes CustomerLead contains getFullName().
             *
             * This is consistent with the FollowUpMapper you
             * provided earlier, where lead.getFullName() was
             * used.
             */
            response.setLeadName(
                    customerLead.getFullName()
            );
        }


        // ======================================================
        // Note Information
        // ======================================================

        response.setTitle(
                note.getTitle()
        );

        response.setContent(
                note.getContent()
        );


        // ======================================================
        // Note Flags
        // ======================================================

        response.setPinned(
                note.isPinned()
        );

        response.setImportant(
                note.isImportant()
        );


        // ======================================================
        // Created By Information
        // ======================================================

        User createdBy =
                note.getCreatedBy();

        if (createdBy != null) {

            response.setCreatedById(
                    createdBy.getId()
            );

            response.setCreatedByName(
                    getUserDisplayName(createdBy)
            );
        }


        // ======================================================
        // Updated By Information
        // ======================================================

        User updatedBy =
                note.getUpdatedBy();

        if (updatedBy != null) {

            response.setUpdatedById(
                    updatedBy.getId()
            );

            response.setUpdatedByName(
                    getUserDisplayName(updatedBy)
            );
        }


        // ======================================================
        // Soft Delete Information
        // ======================================================

        response.setDeleted(
                note.isDeleted()
        );

        response.setDeletedAt(
                note.getDeletedAt()
        );


        // ======================================================
        // Deleted By Information
        // ======================================================

        User deletedBy =
                note.getDeletedBy();

        if (deletedBy != null) {

            response.setDeletedById(
                    deletedBy.getId()
            );

            response.setDeletedByName(
                    getUserDisplayName(deletedBy)
            );
        }


        // ======================================================
        // Audit Information
        // ======================================================

        response.setCreatedAt(
                note.getCreatedAt()
        );

        response.setUpdatedAt(
                note.getUpdatedAt()
        );


        return response;
    }


    // ==========================================================
    // Request DTO -> Entity
    // ==========================================================

    /**
     * Converts NoteRequest into a new Note entity.
     *
     * Important:
     *
     * CustomerLead is NOT assigned here because the request
     * contains only leadId.
     *
     * The service layer should:
     *
     *  1. Find CustomerLead using leadId.
     *  2. Set CustomerLead on the Note.
     *  3. Find authenticated User.
     *  4. Set createdBy and updatedBy.
     *
     * @param request NoteRequest DTO
     * @return Note entity
     */
    public static Note toEntity(
            NoteRequest request) {

        if (request == null) {
            return null;
        }

        Note note = new Note();


        // ======================================================
        // Note Information
        // ======================================================

        note.setTitle(
                request.getTitle()
        );

        note.setContent(
                request.getContent()
        );


        // ======================================================
        // Note Flags
        // ======================================================

        note.setPinned(
                request.isPinned()
        );

        note.setImportant(
                request.isImportant()
        );


        // ======================================================
        // New Note State
        // ======================================================

        /*
         * New notes should always start as active.
         *
         * @PrePersist in Note also initializes the audit
         * timestamps.
         */

        note.setDeleted(false);

        note.setDeletedAt(null);

        note.setDeletedBy(null);


        /*
         * CustomerLead, createdBy and updatedBy are
         * intentionally NOT assigned here.
         *
         * They must be resolved by the service layer.
         */

        return note;
    }


    // ==========================================================
    // Update Existing Entity
    // ==========================================================

    /**
     * Updates an existing Note using NoteRequest.
     *
     * The following fields are intentionally NOT modified:
     *
     *  - noteId
     *  - customerLead
     *  - createdBy
     *  - updatedBy
     *  - deleted
     *  - deletedAt
     *  - deletedBy
     *  - createdAt
     *  - updatedAt
     *
     * These fields are controlled by the service/entity lifecycle.
     *
     * @param note existing Note entity
     * @param request NoteRequest DTO
     */
    public static void updateEntity(
            Note note,
            NoteRequest request) {

        if (note == null || request == null) {
            return;
        }


        // ======================================================
        // Note Information
        // ======================================================

        note.setTitle(
                request.getTitle()
        );

        note.setContent(
                request.getContent()
        );


        // ======================================================
        // Note Flags
        // ======================================================

        note.setPinned(
                request.isPinned()
        );

        note.setImportant(
                request.isImportant()
        );


        /*
         * Do NOT update:
         *
         * customerLead
         * createdBy
         * updatedBy
         * deleted
         * deletedAt
         * deletedBy
         * createdAt
         * updatedAt
         *
         * These are handled by the service layer.
         */
    }


    // ==========================================================
    // User Display Name
    // ==========================================================

    /**
     * Returns the best available display name for a User.
     *
     * Priority:
     *
     *  1. Full name
     *  2. Username
     *
     * @param user User entity
     * @return display name
     */
    private static String getUserDisplayName(
            User user) {

        if (user == null) {
            return null;
        }


        // ------------------------------------------------------
        // Prefer Full Name
        // ------------------------------------------------------

        if (user.getFullName() != null
                && !user.getFullName().isBlank()) {

            return user.getFullName();
        }


        // ------------------------------------------------------
        // Fall Back To Username
        // ------------------------------------------------------

        if (user.getUsername() != null
                && !user.getUsername().isBlank()) {

            return user.getUsername();
        }


        return null;
    }
}