package com.leadpilot.crm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;

/**
 * ==========================================================
 * Repository : FollowUpRepository
 *
 * Description :
 * Handles all database operations related to FollowUp.
 *
 * FollowUp records scheduled actions for customer leads such as:
 *
 *  - Calls
 *  - Emails
 *  - WhatsApp
 *  - Meetings
 *  - Visits
 *  - Other follow-up activities
 *
 * Used by the service layer for:
 *
 *  - Creating follow-ups
 *  - Viewing follow-ups
 *  - Updating follow-ups
 *  - Filtering by lead
 *  - Filtering by assigned executive
 *  - Filtering by status
 *  - Filtering by type
 *  - Finding today's follow-ups
 *  - Finding upcoming follow-ups
 *  - Finding overdue follow-ups
 *
 * Spring Data JPA automatically provides the implementation.
 * ==========================================================
 */
@Repository
public interface FollowUpRepository
        extends JpaRepository<FollowUp, Long> {

    // ==========================================================
    // Find All Follow-Ups Of A Particular Lead
    // Latest Scheduled First
    // ==========================================================

    List<FollowUp> findByCustomerLeadOrderByScheduledAtDesc(
            CustomerLead customerLead
    );

    // ==========================================================
    // Find All Follow-Ups Assigned To A Particular User
    // Latest Scheduled First
    // ==========================================================

    List<FollowUp> findByAssignedUserOrderByScheduledAtDesc(
            User assignedUser
    );

    // ==========================================================
    // Find Follow-Ups By Status
    // ==========================================================

    List<FollowUp> findByStatus(
            FollowUpStatus status
    );

    // ==========================================================
    // Find Follow-Ups By Type
    // ==========================================================

    List<FollowUp> findByFollowUpType(
            FollowUpType followUpType
    );

    // ==========================================================
    // Find Follow-Ups Of A Lead By Status
    // ==========================================================

    List<FollowUp> findByCustomerLeadAndStatus(
            CustomerLead customerLead,
            FollowUpStatus status
    );

    // ==========================================================
    // Find Follow-Ups Assigned To User By Status
    // ==========================================================

    List<FollowUp> findByAssignedUserAndStatus(
            User assignedUser,
            FollowUpStatus status
    );

    // ==========================================================
    // Find Follow-Ups Of A Lead By Type
    // ==========================================================

    List<FollowUp> findByCustomerLeadAndFollowUpType(
            CustomerLead customerLead,
            FollowUpType followUpType
    );

    // ==========================================================
    // Find Follow-Ups Assigned To User By Type
    // ==========================================================

    List<FollowUp> findByAssignedUserAndFollowUpType(
            User assignedUser,
            FollowUpType followUpType
    );

    // ==========================================================
    // Find Follow-Ups Between Two Dates
    //
    // Useful for:
    //  - Today's follow-ups
    //  - Daily schedule
    //  - Weekly schedule
    //  - Monthly dashboard
    // ==========================================================

    List<FollowUp> findByScheduledAtBetweenOrderByScheduledAtAsc(
            LocalDateTime start,
            LocalDateTime end
    );

    // ==========================================================
    // Find Follow-Ups Of A User Between Two Dates
    //
    // Useful for executive dashboard.
    // ==========================================================

    List<FollowUp> findByAssignedUserAndScheduledAtBetweenOrderByScheduledAtAsc(
            User assignedUser,
            LocalDateTime start,
            LocalDateTime end
    );

    // ==========================================================
    // Find Follow-Ups Of A Lead Between Two Dates
    // ==========================================================

    List<FollowUp> findByCustomerLeadAndScheduledAtBetweenOrderByScheduledAtAsc(
            CustomerLead customerLead,
            LocalDateTime start,
            LocalDateTime end
    );

    // ==========================================================
    // Find Today's Follow-Ups
    //
    // The service layer should provide:
    // start = today 00:00:00
    // end   = tomorrow 00:00:00
    // ==========================================================

    List<FollowUp> findByScheduledAtGreaterThanEqualAndScheduledAtLessThanOrderByScheduledAtAsc(
            LocalDateTime start,
            LocalDateTime end
    );

    // ==========================================================
    // Find Today's Follow-Ups Of A Particular User
    // ==========================================================

    List<FollowUp> findByAssignedUserAndScheduledAtGreaterThanEqualAndScheduledAtLessThanOrderByScheduledAtAsc(
            User assignedUser,
            LocalDateTime start,
            LocalDateTime end
    );

    // ==========================================================
    // Find Upcoming Follow-Ups
    //
    // scheduledAt >= current time
    // ==========================================================

    List<FollowUp> findByScheduledAtGreaterThanEqualAndStatusNotOrderByScheduledAtAsc(
            LocalDateTime currentTime,
            FollowUpStatus status
    );

    // ==========================================================
    // Find Upcoming Follow-Ups Of A User
    // ==========================================================

    List<FollowUp> findByAssignedUserAndScheduledAtGreaterThanEqualAndStatusNotOrderByScheduledAtAsc(
            User assignedUser,
            LocalDateTime currentTime,
            FollowUpStatus status
    );

    // ==========================================================
    // Find Overdue Follow-Ups
    //
    // Follow-up time has passed and it is not completed/cancelled.
    //
    // The service layer can call this using:
    // scheduledAt < currentTime
    // ==========================================================

    List<FollowUp> findByScheduledAtLessThanAndStatusOrderByScheduledAtAsc(
            LocalDateTime currentTime,
            FollowUpStatus status
    );

    // ==========================================================
    // Find Overdue Follow-Ups Of A User
    // ==========================================================

    List<FollowUp> findByAssignedUserAndScheduledAtLessThanAndStatusOrderByScheduledAtAsc(
            User assignedUser,
            LocalDateTime currentTime,
            FollowUpStatus status
    );

    // ==========================================================
    // Find Follow-Ups By Status And Date Range
    // ==========================================================

    List<FollowUp> findByStatusAndScheduledAtBetweenOrderByScheduledAtAsc(
            FollowUpStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    // ==========================================================
    // Find User Follow-Ups By Status And Date Range
    // ==========================================================

    List<FollowUp> findByAssignedUserAndStatusAndScheduledAtBetweenOrderByScheduledAtAsc(
            User assignedUser,
            FollowUpStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    // ==========================================================
    // Find Completed Follow-Ups Of A Lead
    // ==========================================================

    List<FollowUp> findByCustomerLeadAndStatusOrderByCompletedAtDesc(
            CustomerLead customerLead,
            FollowUpStatus status
    );

    // ==========================================================
    // Find Completed Follow-Ups By User
    // ==========================================================

    List<FollowUp> findByAssignedUserAndStatusOrderByCompletedAtDesc(
            User assignedUser,
            FollowUpStatus status
    );

    // ==========================================================
    // Find Follow-Ups By Type And Status
    // ==========================================================

    List<FollowUp> findByFollowUpTypeAndStatus(
            FollowUpType followUpType,
            FollowUpStatus status
    );

    // ==========================================================
    // Count Follow-Ups By Status
    //
    // Useful for dashboard statistics.
    // ==========================================================

    long countByStatus(
            FollowUpStatus status
    );

    // ==========================================================
    // Count Follow-Ups Of A Particular User By Status
    //
    // Useful for Executive Dashboard.
    // ==========================================================

    long countByAssignedUserAndStatus(
            User assignedUser,
            FollowUpStatus status
    );

    // ==========================================================
    // Count Follow-Ups Of A Particular Lead
    // ==========================================================

    long countByCustomerLead(
            CustomerLead customerLead
    );

    // ==========================================================
    // Count Follow-Ups Of A User
    // ==========================================================

    long countByAssignedUser(
            User assignedUser
    );

    // ==========================================================
    // Find All Follow-Ups Ordered By Scheduled Date
    // ==========================================================

    List<FollowUp> findAllByOrderByScheduledAtAsc();

    // ==========================================================
    // Find All Follow-Ups Ordered By Latest Creation
    // ==========================================================

    List<FollowUp> findAllByOrderByCreatedAtDesc();

}