package com.leadpilot.crm.service;

import java.util.List;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.entity.LeadSubCategory;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.LeadPriority;
import com.leadpilot.crm.enums.LeadStatus;

/**
 * ==========================================================
 * Service : CustomerLeadService
 *
 * Description :
 * Defines business operations related to CustomerLead.
 *
 * Operations include:
 *
 * - Create lead
 * - View all leads
 * - View lead by ID
 * - Update lead
 * - Delete lead
 * - Assign lead
 * - Unassign lead
 * - Search/filter leads
 * - Filter leads by category
 * - Filter leads by subcategory
 * - Filter leads by source
 * - Filter leads by status
 * - Filter leads by priority
 *
 * ==========================================================
 */
public interface CustomerLeadService {

    // ==========================================================
    // Create Lead
    // ==========================================================

    /**
     * Creates a new customer lead.
     */
    CustomerLead createLead(CustomerLead customerLead);


    // ==========================================================
    // Get All Leads
    // ==========================================================

    /**
     * Retrieves all customer leads.
     */
    List<CustomerLead> getAllLeads();


    // ==========================================================
    // Get Lead By ID
    // ==========================================================

    /**
     * Retrieves a customer lead by its ID.
     */
    CustomerLead getLeadById(Long leadId);


    // ==========================================================
    // Update Lead
    // ==========================================================

    /**
     * Updates an existing customer lead.
     */
    CustomerLead updateLead(
            Long leadId,
            CustomerLead customerLead
    );


    // ==========================================================
    // Delete Lead
    // ==========================================================

    /**
     * Permanently deletes a customer lead.
     */
    void deleteLead(Long leadId);


    // ==========================================================
    // Assigned Executive / User
    // ==========================================================

    /**
     * Finds all leads assigned to a particular user.
     */
    List<CustomerLead> getLeadsByAssignedUser(
            User assignedUser
    );


    // ==========================================================
    // Lead Status
    // ==========================================================

    /**
     * Finds all leads having a particular status.
     */
    List<CustomerLead> getLeadsByStatus(
            LeadStatus leadStatus
    );


    // ==========================================================
    // Lead Priority
    // ==========================================================

    /**
     * Finds all leads having a particular priority.
     */
    List<CustomerLead> getLeadsByPriority(
            LeadPriority leadPriority
    );


    // ==========================================================
    // Lead Category
    // ==========================================================

    /**
     * Finds all leads belonging to a particular category.
     */
    List<CustomerLead> getLeadsByCategory(
            LeadCategory leadCategory
    );


    // ==========================================================
    // Lead Category By ID
    // ==========================================================

    /**
     * Finds all leads belonging to a particular category ID.
     */
    List<CustomerLead> getLeadsByCategoryId(
            Long categoryId
    );


    // ==========================================================
    // Lead Subcategory
    // ==========================================================

    /**
     * Finds all leads belonging to a particular subcategory.
     */
    List<CustomerLead> getLeadsBySubCategory(
            LeadSubCategory leadSubCategory
    );


    // ==========================================================
    // Lead Subcategory By ID
    // ==========================================================

    /**
     * Finds all leads belonging to a particular subcategory ID.
     */
    List<CustomerLead> getLeadsBySubCategoryId(
            Long subCategoryId
    );


    // ==========================================================
    // Lead Source
    // ==========================================================

    /**
     * Finds all leads coming from a particular lead source.
     */
    List<CustomerLead> getLeadsBySource(
            LeadSource leadSource
    );


    // ==========================================================
    // Lead Source By ID
    // ==========================================================

    /**
     * Finds all leads coming from a particular source ID.
     */
    List<CustomerLead> getLeadsBySourceId(
            Long sourceId
    );


    // ==========================================================
    // Status + Priority
    // ==========================================================

    /**
     * Finds leads by both status and priority.
     */
    List<CustomerLead> getLeadsByStatusAndPriority(
            LeadStatus leadStatus,
            LeadPriority leadPriority
    );


    // ==========================================================
    // Category + Subcategory
    // ==========================================================

    /**
     * Finds leads by both category and subcategory.
     */
    List<CustomerLead> getLeadsByCategoryAndSubCategory(
            LeadCategory leadCategory,
            LeadSubCategory leadSubCategory
    );


    // ==========================================================
    // Assigned User + Status
    // ==========================================================

    /**
     * Finds leads assigned to a particular user
     * and having a particular status.
     */
    List<CustomerLead> getLeadsByAssignedUserAndStatus(
            User assignedUser,
            LeadStatus leadStatus
    );


    // ==========================================================
    // Assigned User + Priority
    // ==========================================================

    /**
     * Finds leads assigned to a particular user
     * and having a particular priority.
     */
    List<CustomerLead> getLeadsByAssignedUserAndPriority(
            User assignedUser,
            LeadPriority leadPriority
    );


    // ==========================================================
    // Assign Lead
    // ==========================================================

    /**
     * Assigns a lead to an executive/user.
     */
    CustomerLead assignLead(
            Long leadId,
            Long assignedUserId
    );


    // ==========================================================
    // Unassign Lead
    // ==========================================================

    /**
     * Removes the currently assigned user from a lead.
     */
    CustomerLead unassignLead(
            Long leadId
    );
}