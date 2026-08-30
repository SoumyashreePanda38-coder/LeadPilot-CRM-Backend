package com.leadpilot.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.LeadActivity;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.LeadActivityType;

/**
 * ==========================================================
 * Repository : LeadActivityRepository
 *
 * Description :
 * Handles all database operations related to LeadActivity.
 *
 * LeadActivity stores the complete history/timeline of
 * activities performed on a customer lead.
 *
 * Examples:
 *
 * - Lead created
 * - Lead updated
 * - Lead assigned
 * - Lead reassigned
 * - Call made
 * - Email sent
 * - WhatsApp communication
 * - Meeting completed
 * - Follow-up completed
 * - Status changed
 * - Priority changed
 * - Note added
 * - Lead converted
 *
 * Spring Data JPA automatically provides the implementation.
 * ==========================================================
 */

@Repository
public interface LeadActivityRepository
        extends JpaRepository<LeadActivity, Long> {

    // ==========================================================
    // Find All Activities Of A Particular Lead
    // ==========================================================

    List<LeadActivity> findByCustomerLeadOrderByCreatedAtDesc(
            CustomerLead customerLead
    );

    // ==========================================================
    // Find Activities By Activity Type
    // ==========================================================

    List<LeadActivity> findByActivityType(
            LeadActivityType activityType
    );

    // ==========================================================
    // Find Activities Performed By A Particular User
    // ==========================================================

    List<LeadActivity> findByPerformedBy(
            User performedBy
    );

    // ==========================================================
    // Find Activities Of A Lead By Activity Type
    // ==========================================================

    List<LeadActivity> findByCustomerLeadAndActivityType(
            CustomerLead customerLead,
            LeadActivityType activityType
    );

    // ==========================================================
    // Find Activities Of A Lead Performed By A User
    // ==========================================================

    List<LeadActivity> findByCustomerLeadAndPerformedBy(
            CustomerLead customerLead,
            User performedBy
    );

    // ==========================================================
    // Find Activities By Lead And Activity Type
    // Ordered By Latest Activity
    // ==========================================================

    List<LeadActivity>
    findByCustomerLeadAndActivityTypeOrderByCreatedAtDesc(
            CustomerLead customerLead,
            LeadActivityType activityType
    );

    // ==========================================================
    // Find All Activities Performed By A User
    // Ordered By Latest Activity
    // ==========================================================

    List<LeadActivity>
    findByPerformedByOrderByCreatedAtDesc(
            User performedBy
    );

    // ==========================================================
    // Find All Activities Of A Particular Lead
    // Ordered From Oldest To Newest
    // ==========================================================

    List<LeadActivity> findByCustomerLeadOrderByCreatedAtAsc(
            CustomerLead customerLead
    );

}