package com.leadpilot.crm.service;

import java.util.List;

import com.leadpilot.crm.dto.LeadActivityRequest;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.LeadActivity;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.LeadActivityType;

/**
 * ==========================================================
 * Service : LeadActivityService
 *
 * Description :
 * Defines business operations for managing the activity
 * history/timeline of customer leads.
 *
 * Lead activities include:
 *
 * - Lead creation and updates
 * - Lead assignment/reassignment
 * - Calls, emails, SMS and WhatsApp
 * - Meetings and visits
 * - Status and priority changes
 * - Category/subcategory/source changes
 * - Follow-ups
 * - Notes
 * - Lead conversion
 * - Lead closure/reopening
 * - Documents
 * - Import/export
 * - Administrative activities
 *
 * The implementation will be handled by:
 * LeadActivityServiceImpl
 * ==========================================================
 */

public interface LeadActivityService {

    // ==========================================================
    // CREATE ACTIVITY
    // ==========================================================

    LeadActivity createActivity(
            LeadActivityRequest request
    );

    // ==========================================================
    // GET ACTIVITY BY ID
    // ==========================================================

    LeadActivity getActivityById(
            Long activityId
    );

    // ==========================================================
    // GET ALL ACTIVITIES
    // ==========================================================

    List<LeadActivity> getAllActivities();

    // ==========================================================
    // GET ALL ACTIVITIES OF A PARTICULAR LEAD
    // Latest First
    // ==========================================================

    List<LeadActivity> getActivitiesByLead(
            CustomerLead customerLead
    );

    // ==========================================================
    // GET ALL ACTIVITIES OF A PARTICULAR LEAD
    // Oldest First
    // ==========================================================

    List<LeadActivity> getActivitiesByLeadOldestFirst(
            CustomerLead customerLead
    );

    // ==========================================================
    // GET ACTIVITIES BY ACTIVITY TYPE
    // ==========================================================

    List<LeadActivity> getActivitiesByType(
            LeadActivityType activityType
    );

    // ==========================================================
    // GET ACTIVITIES PERFORMED BY A USER
    // ==========================================================

    List<LeadActivity> getActivitiesByUser(
            User performedBy
    );

    // ==========================================================
    // GET ACTIVITIES OF A LEAD BY ACTIVITY TYPE
    // ==========================================================

    List<LeadActivity> getActivitiesByLeadAndType(
            CustomerLead customerLead,
            LeadActivityType activityType
    );

    // ==========================================================
    // GET ACTIVITIES OF A LEAD PERFORMED BY A USER
    // ==========================================================

    List<LeadActivity> getActivitiesByLeadAndUser(
            CustomerLead customerLead,
            User performedBy
    );

    // ==========================================================
    // GET ACTIVITIES OF A LEAD BY TYPE
    // Latest First
    // ==========================================================

    List<LeadActivity> getActivitiesByLeadAndTypeLatestFirst(
            CustomerLead customerLead,
            LeadActivityType activityType
    );

    // ==========================================================
    // GET ALL ACTIVITIES PERFORMED BY USER
    // Latest First
    // ==========================================================

    List<LeadActivity> getActivitiesByUserLatestFirst(
            User performedBy
    );

}