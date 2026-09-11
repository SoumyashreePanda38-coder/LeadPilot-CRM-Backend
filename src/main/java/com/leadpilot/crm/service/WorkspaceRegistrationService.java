package com.leadpilot.crm.service;

import com.leadpilot.crm.dto.WorkspaceRegistrationRequest;
import com.leadpilot.crm.dto.WorkspaceRegistrationResponse;

/**
 * ==========================================================
 * Service : WorkspaceRegistrationService
 *
 * Description :
 * Defines the business operations required to create a new
 * LeadPilot CRM workspace and its first Admin user.
 *
 * ==========================================================
 */

public interface WorkspaceRegistrationService {

    /**
     * Creates a new organization/workspace and its first
     * Admin user.
     *
     * @param request workspace and admin registration details
     * @return registration response
     */
    WorkspaceRegistrationResponse registerWorkspace(
            WorkspaceRegistrationRequest request
    );
}