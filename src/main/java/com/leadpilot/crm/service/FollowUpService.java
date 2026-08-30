package com.leadpilot.crm.service;

import java.time.LocalDateTime;
import java.util.List;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;

/**
 * ==========================================================
 * Service : FollowUpService
 *
 * Description :
 * Defines business operations for managing follow-ups
 * associated with customer leads.
 *
 * A follow-up represents a planned action such as:
 *
 *  - Phone call
 *  - Email
 *  - WhatsApp
 *  - Meeting
 *  - Site / property visit
 *  - Other customer interactions
 *
 * The service is used by both:
 *
 *  - Admin Dashboard
 *  - Executive Dashboard
 *
 * It provides operations for:
 *
 *  - Creating follow-ups
 *  - Viewing follow-ups
 *  - Updating follow-ups
 *  - Deleting follow-ups
 *  - Completing follow-ups
 *  - Cancelling follow-ups
 *  - Filtering by lead
 *  - Filtering by executive
 *  - Filtering by status
 *  - Filtering by type
 *  - Today's follow-ups
 *  - Upcoming follow-ups
 *  - Overdue follow-ups
 *  - Dashboard statistics
 *
 * Implementation:
 * FollowUpServiceImpl
 *
 * ==========================================================
 */
public interface FollowUpService {

    // ==========================================================
    // CREATE
    // ==========================================================

    /**
     * Create a new follow-up.
     *
     * @param followUp follow-up information
     * @return saved follow-up
     */
    FollowUp createFollowUp(
            FollowUp followUp
    );


    // ==========================================================
    // READ
    // ==========================================================

    /**
     * Get all follow-ups.
     *
     * Mainly useful for Admin.
     *
     * @return list of all follow-ups
     */
    List<FollowUp> getAllFollowUps();


    /**
     * Get follow-up by ID.
     *
     * @param followUpId follow-up ID
     * @return follow-up
     */
    FollowUp getFollowUpById(
            Long followUpId
    );


    // ==========================================================
    // UPDATE
    // ==========================================================

    /**
     * Update an existing follow-up.
     *
     * @param followUpId follow-up ID
     * @param followUp updated follow-up information
     * @return updated follow-up
     */
    FollowUp updateFollowUp(
            Long followUpId,
            FollowUp followUp
    );


    // ==========================================================
    // DELETE
    // ==========================================================

    /**
     * Delete a follow-up.
     *
     * Physical deletion should normally be used only when
     * the follow-up has not become important historical data.
     *
     * @param followUpId follow-up ID
     */
    void deleteFollowUp(
            Long followUpId
    );


    // ==========================================================
    // LEAD-BASED OPERATIONS
    // ==========================================================

    /**
     * Get all follow-ups belonging to a particular lead.
     *
     * Latest scheduled follow-up first.
     *
     * @param customerLead customer lead
     * @return follow-ups of the lead
     */
    List<FollowUp> getFollowUpsByLead(
            CustomerLead customerLead
    );


    /**
     * Get follow-ups of a lead between two dates.
     *
     * @param customerLead customer lead
     * @param start start date/time
     * @param end end date/time
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByLeadAndDateRange(
            CustomerLead customerLead,
            LocalDateTime start,
            LocalDateTime end
    );


    /**
     * Get follow-ups of a lead by status.
     *
     * @param customerLead customer lead
     * @param status follow-up status
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByLeadAndStatus(
            CustomerLead customerLead,
            FollowUpStatus status
    );


    /**
     * Get follow-ups of a lead by type.
     *
     * @param customerLead customer lead
     * @param followUpType follow-up type
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByLeadAndType(
            CustomerLead customerLead,
            FollowUpType followUpType
    );


    // ==========================================================
    // EXECUTIVE / USER-BASED OPERATIONS
    // ==========================================================

    /**
     * Get all follow-ups assigned to a particular user.
     *
     * Mainly used by Executive Dashboard.
     *
     * @param assignedUser responsible user
     * @return user's follow-ups
     */
    List<FollowUp> getFollowUpsByUser(
            User assignedUser
    );


    /**
     * Get follow-ups assigned to a user between two dates.
     *
     * @param assignedUser responsible user
     * @param start start date/time
     * @param end end date/time
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByUserAndDateRange(
            User assignedUser,
            LocalDateTime start,
            LocalDateTime end
    );


    /**
     * Get follow-ups assigned to a user by status.
     *
     * @param assignedUser responsible user
     * @param status follow-up status
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByUserAndStatus(
            User assignedUser,
            FollowUpStatus status
    );


    /**
     * Get follow-ups assigned to a user by type.
     *
     * @param assignedUser responsible user
     * @param followUpType follow-up type
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByUserAndType(
            User assignedUser,
            FollowUpType followUpType
    );


    // ==========================================================
    // STATUS OPERATIONS
    // ==========================================================

    /**
     * Get all follow-ups by status.
     *
     * @param status follow-up status
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByStatus(
            FollowUpStatus status
    );


    /**
     * Update the status of a follow-up.
     *
     * Useful for:
     *
     *  - Scheduled
     *  - Completed
     *  - Cancelled
     *  - Missed
     *
     * @param followUpId follow-up ID
     * @param status new status
     * @return updated follow-up
     */
    FollowUp updateFollowUpStatus(
            Long followUpId,
            FollowUpStatus status
    );


    /**
     * Complete a follow-up.
     *
     * This should also record completedAt and outcome
     * inside the service implementation.
     *
     * @param followUpId follow-up ID
     * @param outcome result of the follow-up
     * @return completed follow-up
     */
    FollowUp completeFollowUp(
            Long followUpId,
            String outcome
    );


    /**
     * Cancel a follow-up.
     *
     * @param followUpId follow-up ID
     * @return cancelled follow-up
     */
    FollowUp cancelFollowUp(
            Long followUpId
    );


    // ==========================================================
    // TYPE OPERATIONS
    // ==========================================================

    /**
     * Get all follow-ups of a particular type.
     *
     * Examples:
     *
     *  - CALL
     *  - EMAIL
     *  - WHATSAPP
     *  - MEETING
     *  - VISIT
     *
     * @param followUpType follow-up type
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByType(
            FollowUpType followUpType
    );


    /**
     * Get follow-ups by type and status.
     *
     * @param followUpType follow-up type
     * @param status follow-up status
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByTypeAndStatus(
            FollowUpType followUpType,
            FollowUpStatus status
    );


    // ==========================================================
    // DATE / SCHEDULE OPERATIONS
    // ==========================================================

    /**
     * Get follow-ups between two dates.
     *
     * Useful for:
     *
     *  - Daily schedule
     *  - Weekly schedule
     *  - Monthly calendar
     *
     * @param start start date/time
     * @param end end date/time
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsBetween(
            LocalDateTime start,
            LocalDateTime end
    );


    /**
     * Get today's follow-ups.
     *
     * Used heavily by both dashboards.
     *
     * @return today's follow-ups
     */
    List<FollowUp> getTodaysFollowUps();


    /**
     * Get today's follow-ups of a particular executive.
     *
     * @param assignedUser executive
     * @return today's follow-ups
     */
    List<FollowUp> getTodaysFollowUpsByUser(
            User assignedUser
    );


    /**
     * Get upcoming follow-ups.
     *
     * @param currentTime current date/time
     * @return upcoming follow-ups
     */
    List<FollowUp> getUpcomingFollowUps(
            LocalDateTime currentTime
    );


    /**
     * Get upcoming follow-ups assigned to a particular user.
     *
     * @param assignedUser executive
     * @param currentTime current date/time
     * @return upcoming follow-ups
     */
    List<FollowUp> getUpcomingFollowUpsByUser(
            User assignedUser,
            LocalDateTime currentTime
    );


    /**
     * Get overdue follow-ups.
     *
     * @param currentTime current date/time
     * @return overdue follow-ups
     */
    List<FollowUp> getOverdueFollowUps(
            LocalDateTime currentTime
    );


    /**
     * Get overdue follow-ups assigned to a particular user.
     *
     * @param assignedUser executive
     * @param currentTime current date/time
     * @return overdue follow-ups
     */
    List<FollowUp> getOverdueFollowUpsByUser(
            User assignedUser,
            LocalDateTime currentTime
    );


    // ==========================================================
    // DASHBOARD OPERATIONS
    // ==========================================================

    /**
     * Count follow-ups by status.
     *
     * Used by Admin Dashboard.
     *
     * @param status follow-up status
     * @return number of follow-ups
     */
    long countFollowUpsByStatus(
            FollowUpStatus status
    );


    /**
     * Count follow-ups assigned to a user by status.
     *
     * Used by Executive Dashboard.
     *
     * @param assignedUser executive
     * @param status follow-up status
     * @return number of follow-ups
     */
    long countFollowUpsByUserAndStatus(
            User assignedUser,
            FollowUpStatus status
    );


    /**
     * Count total follow-ups of a lead.
     *
     * @param customerLead customer lead
     * @return total follow-ups
     */
    long countFollowUpsByLead(
            CustomerLead customerLead
    );


    /**
     * Count total follow-ups assigned to a user.
     *
     * @param assignedUser executive
     * @return total follow-ups
     */
    long countFollowUpsByUser(
            User assignedUser
    );


    // ==========================================================
    // SORTED / TIMELINE OPERATIONS
    // ==========================================================

    /**
     * Get all follow-ups ordered by scheduled date.
     *
     * @return scheduled follow-ups
     */
    List<FollowUp> getFollowUpsByScheduledDate();


    /**
     * Get recently created follow-ups.
     *
     * Useful for Admin Dashboard recent activity.
     *
     * @return recently created follow-ups
     */
    List<FollowUp> getRecentlyCreatedFollowUps();


    /**
     * Get completed follow-ups of a lead.
     *
     * Useful for lead history/timeline.
     *
     * @param customerLead customer lead
     * @return completed follow-ups
     */
    List<FollowUp> getCompletedFollowUpsByLead(
            CustomerLead customerLead
    );


    /**
     * Get completed follow-ups performed by a user.
     *
     * Useful for executive activity history.
     *
     * @param assignedUser executive
     * @return completed follow-ups
     */
    List<FollowUp> getCompletedFollowUpsByUser(
            User assignedUser
    );


    // ==========================================================
    // SEARCH / CALENDAR SUPPORT
    // ==========================================================

    /**
     * Get follow-ups by status within a date range.
     *
     * Useful for calendar and dashboard filtering.
     *
     * @param status follow-up status
     * @param start start date/time
     * @param end end date/time
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByStatusAndDateRange(
            FollowUpStatus status,
            LocalDateTime start,
            LocalDateTime end
    );


    /**
     * Get a user's follow-ups by status within a date range.
     *
     * Useful for Executive Dashboard calendar.
     *
     * @param assignedUser executive
     * @param status follow-up status
     * @param start start date/time
     * @param end end date/time
     * @return matching follow-ups
     */
    List<FollowUp> getFollowUpsByUserStatusAndDateRange(
            User assignedUser,
            FollowUpStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

}