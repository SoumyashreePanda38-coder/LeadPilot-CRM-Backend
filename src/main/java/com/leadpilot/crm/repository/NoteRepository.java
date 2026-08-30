package com.leadpilot.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.Note;

/**
 * ==========================================================
 * Repository : NoteRepository
 *
 * Description :
 * Provides database operations for Note entities.
 *
 * Supports:
 *
 * - Create Note
 * - Find Note by ID
 * - Find all Notes
 * - Find active Notes
 * - Find deleted Notes
 * - Find Notes by Lead
 * - Find active Notes by Lead
 * - Find deleted Notes by Lead
 * - Find pinned Notes
 * - Find important Notes
 * - Search Notes
 * - Find Notes by User
 *
 * ==========================================================
 */

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // ==========================================================
    // FIND NOTE BY ID
    // ==========================================================

    Optional<Note> findByNoteId(Long noteId);


    // ==========================================================
    // ACTIVE NOTES
    // ==========================================================

    /**
     * Finds all notes which have not been soft deleted.
     */
    List<Note> findByDeletedFalse();


    /**
     * Finds all active notes ordered by creation date,
     * newest first.
     */
    List<Note> findByDeletedFalseOrderByCreatedAtDesc();


    // ==========================================================
    // DELETED NOTES
    // ==========================================================

    /**
     * Finds all soft-deleted notes.
     */
    List<Note> findByDeletedTrue();


    /**
     * Finds all deleted notes ordered by deletion date,
     * newest first.
     */
    List<Note> findByDeletedTrueOrderByDeletedAtDesc();


    // ==========================================================
    // NOTES BY LEAD
    // ==========================================================

    /**
     * Finds all notes belonging to a particular lead.
     */
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.customerLead.leadId = :leadId
        ORDER BY n.createdAt DESC
        """)
    List<Note> findByLeadId(
            @Param("leadId") Long leadId
    );


    /**
     * Finds only active notes belonging to a particular lead.
     */
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.customerLead.leadId = :leadId
        AND n.deleted = false
        ORDER BY n.createdAt DESC
        """)
    List<Note> findActiveNotesByLeadId(
            @Param("leadId") Long leadId
    );


    /**
     * Finds deleted notes belonging to a particular lead.
     */
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.customerLead.leadId = :leadId
        AND n.deleted = true
        ORDER BY n.deletedAt DESC
        """)
    List<Note> findDeletedNotesByLeadId(
            @Param("leadId") Long leadId
    );


    // ==========================================================
    // PINNED NOTES
    // ==========================================================

    /**
     * Finds all active pinned notes.
     */
    List<Note> findByPinnedTrueAndDeletedFalseOrderByCreatedAtDesc();


    /**
     * Finds active pinned notes for a particular lead.
     */
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.customerLead.leadId = :leadId
        AND n.pinned = true
        AND n.deleted = false
        ORDER BY n.createdAt DESC
        """)
    List<Note> findPinnedNotesByLeadId(
            @Param("leadId") Long leadId
    );


    // ==========================================================
    // IMPORTANT NOTES
    // ==========================================================

    /**
     * Finds all active important notes.
     */
    List<Note> findByImportantTrueAndDeletedFalseOrderByCreatedAtDesc();


    /**
     * Finds active important notes for a particular lead.
     */
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.customerLead.leadId = :leadId
        AND n.important = true
        AND n.deleted = false
        ORDER BY n.createdAt DESC
        """)
    List<Note> findImportantNotesByLeadId(
            @Param("leadId") Long leadId
    );


    // ==========================================================
    // SEARCH BY TITLE
    // ==========================================================

    /**
     * Searches active notes by title.
     *
     * This is safe because title is a normal VARCHAR/String
     * column and not a CLOB.
     */
    List<Note> findByTitleContainingIgnoreCaseAndDeletedFalseOrderByCreatedAtDesc(
            String title
    );


    // ==========================================================
    // SEARCH BY TITLE OR CONTENT
    // ==========================================================

    /**
     * Searches active notes by title or content.
     *
     * IMPORTANT:
     * Do not use LOWER(n.content) because content is mapped
     * as a CLOB through @Lob.
     *
     * We therefore use LIKE directly on the content field.
     *
     * Title remains case-insensitive.
     */
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.deleted = false
        AND (
            LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR n.content LIKE CONCAT('%', :keyword, '%')
        )
        ORDER BY n.createdAt DESC
        """)
    List<Note> searchActiveNotes(
            @Param("keyword") String keyword
    );


    // ==========================================================
    // CREATED BY USER
    // ==========================================================

    /**
     * Finds active notes created by a particular user.
     */
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.createdBy.id = :userId
        AND n.deleted = false
        ORDER BY n.createdAt DESC
        """)
    List<Note> findActiveNotesByCreatedById(
            @Param("userId") Long userId
    );


    // ==========================================================
    // EXISTENCE CHECKS
    // ==========================================================

    /**
     * Checks whether an active note exists.
     */
    boolean existsByNoteIdAndDeletedFalse(
            Long noteId
    );


    /**
     * Checks whether a deleted note exists.
     */
    boolean existsByNoteIdAndDeletedTrue(
            Long noteId
    );
}