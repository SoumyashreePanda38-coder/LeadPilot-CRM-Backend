package com.leadpilot.crm.service;

import com.leadpilot.crm.dto.AdminDashboardResponse;
import com.leadpilot.crm.dto.ExecutiveDashboardResponse;

/**
 * ==========================================================
 * Service : DashboardService
 *
 * Description :
 * Provides dashboard data for ADMIN and EXECUTIVE users.
 *
 * ==========================================================
 */
public interface DashboardService {

    // ==========================================================
    // ADMIN DASHBOARD
    // ==========================================================

    /**
     * Build the complete Admin Dashboard.
     *
     * @param userId ID of the logged-in Admin user
     * @return complete Admin dashboard response
     */
    AdminDashboardResponse getAdminDashboard(Long userId);


    // ==========================================================
    // EXECUTIVE DASHBOARD
    // ==========================================================

    /**
     * Build the dashboard for a particular Executive.
     *
     * Only data assigned to the supplied executive should
     * be included in the response.
     *
     * @param userId ID of the logged-in Executive user
     * @return complete Executive dashboard response
     */
    ExecutiveDashboardResponse getExecutiveDashboard(Long userId);
}