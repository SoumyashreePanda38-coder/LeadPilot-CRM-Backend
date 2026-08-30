package com.leadpilot.crm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.dto.LeadSourceRequest;
import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.enums.CategoryStatus;
import com.leadpilot.crm.repository.LeadSourceRepository;
import com.leadpilot.crm.service.LeadSourceService;

/**
 * ==========================================================
 * Service Implementation : LeadSourceServiceImpl
 *
 * Description :
 * Implements business operations related to LeadSource.
 *
 * Supported operations:
 *
 * - Add Lead Source
 * - View All Lead Sources
 * - View Lead Source By ID
 * - View Lead Sources By Status
 * - Update Lead Source
 * - Update Status
 * - Delete Lead Source
 * - Search Lead Sources
 *
 * ==========================================================
 */

@Service
@Transactional
public class LeadSourceServiceImpl
        implements LeadSourceService {

    // ==========================================================
    // Repository Dependency
    // ==========================================================

    @Autowired
    private LeadSourceRepository leadSourceRepository;


    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public LeadSource addLeadSource(
            LeadSourceRequest request) {

        // ------------------------------------------------------
        // Check duplicate source name
        // ------------------------------------------------------

        if (leadSourceRepository
                .existsBySourceName(
                        request.getSourceName()
                )) {

            throw new RuntimeException(
                    "Lead source already exists"
            );
        }


        // ------------------------------------------------------
        // Create LeadSource Entity
        // ------------------------------------------------------

        LeadSource leadSource =
                new LeadSource();


        // ------------------------------------------------------
        // Set Request Fields
        // ------------------------------------------------------

        leadSource.setSourceName(
                request.getSourceName()
        );

        leadSource.setDescription(
                request.getDescription()
        );

        leadSource.setDisplayOrder(
                request.getDisplayOrder()
        );

        leadSource.setStatus(
                request.getStatus()
        );


        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return leadSourceRepository.save(
                leadSource
        );
    }


    // ==========================================================
    // READ ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadSource> getAllLeadSources() {

        return leadSourceRepository
                .findAllByOrderByDisplayOrderAsc();
    }


    // ==========================================================
    // READ BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public LeadSource getLeadSourceById(
            Long leadSourceId) {

        return leadSourceRepository
                .findById(leadSourceId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Lead source not found with ID: "
                                        + leadSourceId
                        )
                );
    }


    // ==========================================================
    // READ BY STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadSource> getLeadSourcesByStatus(
            CategoryStatus status) {

        return leadSourceRepository
                .findByStatusOrderByDisplayOrderAsc(
                        status
                );
    }


    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public LeadSource updateLeadSource(
            Long leadSourceId,
            LeadSourceRequest request) {

        // ------------------------------------------------------
        // Find Existing Lead Source
        // ------------------------------------------------------

        LeadSource existingSource =
                leadSourceRepository
                        .findById(leadSourceId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Lead source not found with ID: "
                                                + leadSourceId
                                )
                        );


        // ------------------------------------------------------
        // Check Duplicate Source Name
        //
        // Only check if the source name is being changed.
        // ------------------------------------------------------

        if (!existingSource
                .getSourceName()
                .equalsIgnoreCase(
                        request.getSourceName()
                )) {

            if (leadSourceRepository
                    .existsBySourceName(
                            request.getSourceName()
                    )) {

                throw new RuntimeException(
                        "Lead source already exists"
                );
            }
        }


        // ------------------------------------------------------
        // Update Allowed Fields
        // ------------------------------------------------------

        existingSource.setSourceName(
                request.getSourceName()
        );

        existingSource.setDescription(
                request.getDescription()
        );

        existingSource.setDisplayOrder(
                request.getDisplayOrder()
        );

        existingSource.setStatus(
                request.getStatus()
        );


        // ------------------------------------------------------
        // Save Updated Entity
        // ------------------------------------------------------

        return leadSourceRepository.save(
                existingSource
        );
    }


    // ==========================================================
    // UPDATE STATUS
    // ==========================================================

    @Override
    public LeadSource updateStatus(
            Long leadSourceId,
            CategoryStatus status) {

        // ------------------------------------------------------
        // Find Existing Source
        // ------------------------------------------------------

        LeadSource existingSource =
                leadSourceRepository
                        .findById(leadSourceId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Lead source not found with ID: "
                                                + leadSourceId
                                )
                        );


        // ------------------------------------------------------
        // Update Status
        // ------------------------------------------------------

        existingSource.setStatus(status);


        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return leadSourceRepository.save(
                existingSource
        );
    }


    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteLeadSource(
            Long leadSourceId) {

        // ------------------------------------------------------
        // Find Existing Source
        // ------------------------------------------------------

        LeadSource existingSource =
                leadSourceRepository
                        .findById(leadSourceId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Lead source not found with ID: "
                                                + leadSourceId
                                )
                        );


        // ------------------------------------------------------
        // Delete
        // ------------------------------------------------------

        leadSourceRepository.delete(
                existingSource
        );
    }


    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadSource> searchLeadSources(
            String keyword) {

        return leadSourceRepository
                .findBySourceNameContainingIgnoreCase(
                        keyword
                );
    }
}