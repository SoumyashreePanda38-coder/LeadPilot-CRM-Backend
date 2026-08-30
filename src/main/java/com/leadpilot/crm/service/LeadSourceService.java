package com.leadpilot.crm.service;

import java.util.List;

import com.leadpilot.crm.dto.LeadSourceRequest;
import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * Service : LeadSourceService
 *
 * Description :
 * Defines business operations for LeadSource.
 * ==========================================================
 */
public interface LeadSourceService {

    // ==========================================================
    // CREATE
    // ==========================================================

    LeadSource addLeadSource(LeadSourceRequest request);


    // ==========================================================
    // READ
    // ==========================================================

    List<LeadSource> getAllLeadSources();

    LeadSource getLeadSourceById(Long leadSourceId);


    // ==========================================================
    // READ BY STATUS
    // ==========================================================

    List<LeadSource> getLeadSourcesByStatus(
            CategoryStatus status
    );


    // ==========================================================
    // UPDATE
    // ==========================================================

    LeadSource updateLeadSource(
            Long leadSourceId,
            LeadSourceRequest request
    );


    // ==========================================================
    // UPDATE STATUS
    // ==========================================================

    LeadSource updateStatus(
            Long leadSourceId,
            CategoryStatus status
    );


    // ==========================================================
    // DELETE
    // ==========================================================

    void deleteLeadSource(Long leadSourceId);


    // ==========================================================
    // SEARCH
    // ==========================================================

    List<LeadSource> searchLeadSources(String keyword);
}