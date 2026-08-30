package com.leadpilot.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.entity.LeadSubCategory;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.LeadPriority;
import com.leadpilot.crm.enums.LeadStatus;

/**
 * ==========================================================
 * Repository : CustomerLeadRepository
 *
 * Description :
 * Handles database operations related to CustomerLead.
 *
 * Spring Data JPA automatically provides the implementation
 * for the methods declared in this interface.
 *
 * ==========================================================
 */

@Repository
public interface CustomerLeadRepository
        extends JpaRepository<CustomerLead, Long> {

    // ==========================================================
    // Assigned Executive / User
    // ==========================================================

    /**
     * Find all leads assigned to a particular user.
     */
    List<CustomerLead> findByAssignedUser(
            User assignedUser
    );


    // ==========================================================
    // Lead Status
    // ==========================================================

    /**
     * Find all leads having a particular status.
     */
    List<CustomerLead> findByLeadStatus(
            LeadStatus leadStatus
    );


    // ==========================================================
    // Lead Priority
    // ==========================================================

    /**
     * Find all leads having a particular priority.
     */
    List<CustomerLead> findByLeadPriority(
            LeadPriority leadPriority
    );


    // ==========================================================
    // Lead Category
    // ==========================================================

    /**
     * Find all leads belonging to a particular category.
     */
    List<CustomerLead> findByLeadCategory(
            LeadCategory leadCategory
    );


    // ==========================================================
    // Lead Category ID
    // ==========================================================

    /**
     * Find all leads belonging to a particular category ID.
     *
     * CustomerLead
     *     -> leadCategory
     *          -> categoryId
     */
    List<CustomerLead> findByLeadCategory_CategoryId(
            Long categoryId
    );


    // ==========================================================
    // Lead Subcategory
    // ==========================================================

    /**
     * Find all leads belonging to a particular subcategory.
     */
    List<CustomerLead> findByLeadSubCategory(
            LeadSubCategory leadSubCategory
    );


    // ==========================================================
    // Lead Subcategory ID
    // ==========================================================

    /**
     * Find all leads belonging to a particular subcategory ID.
     *
     * CustomerLead
     *     -> leadSubCategory
     *          -> subCategoryId
     */
    List<CustomerLead> findByLeadSubCategory_SubCategoryId(
            Long subCategoryId
    );


    // ==========================================================
    // Lead Source
    // ==========================================================

    /**
     * Find all leads coming from a particular lead source.
     */
    List<CustomerLead> findByLeadSource(
            LeadSource leadSource
    );


    // ==========================================================
    // Lead Source ID
    // ==========================================================

    /**
     * Find all leads coming from a particular source ID.
     *
     * CustomerLead
     *     -> leadSource
     *          -> leadSourceId
     */
    List<CustomerLead> findByLeadSource_LeadSourceId(
            Long sourceId
    );


    // ==========================================================
    // Combined Filters
    // ==========================================================

    /**
     * Find leads by status and priority.
     */
    List<CustomerLead> findByLeadStatusAndLeadPriority(
            LeadStatus leadStatus,
            LeadPriority leadPriority
    );


    /**
     * Find leads by category and subcategory.
     */
    List<CustomerLead> findByLeadCategoryAndLeadSubCategory(
            LeadCategory leadCategory,
            LeadSubCategory leadSubCategory
    );


    /**
     * Find leads assigned to a user and having a particular status.
     */
    List<CustomerLead> findByAssignedUserAndLeadStatus(
            User assignedUser,
            LeadStatus leadStatus
    );


    /**
     * Find leads assigned to a user and having a particular priority.
     */
    List<CustomerLead> findByAssignedUserAndLeadPriority(
            User assignedUser,
            LeadPriority leadPriority
    );
}