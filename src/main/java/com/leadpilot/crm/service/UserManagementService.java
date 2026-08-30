package com.leadpilot.crm.service;

import java.util.List;

import com.leadpilot.crm.dto.UserResponse;

/**
 * ==========================================================
 * Service : UserManagementService
 *
 * Description :
 * Handles ADMIN operations related to EXECUTIVE users.
 *
 * ADMIN can:
 *
 * - Create Executive
 * - View all Executives
 * - View Executive by ID
 * - Update Executive
 * - Activate Executive
 * - Deactivate Executive
 *
 * ADMIN cannot create another ADMIN through this service.
 *
 * ==========================================================
 */
public interface UserManagementService {

    // ==========================================================
    // CREATE EXECUTIVE
    // ==========================================================

    /**
     * Creates a new Executive user.
     *
     * @param request executive information
     * @param adminId ID of the Admin creating the Executive
     * @return created Executive
     */
    UserResponse createExecutive(
            com.leadpilot.crm.dto.UserManagementRequest request,
            Long adminId
    );

    // ==========================================================
    // GET ALL EXECUTIVES
    // ==========================================================

    /**
     * Returns all Executive users.
     *
     * @return list of Executives
     */
    List<UserResponse> getAllExecutives();

    // ==========================================================
    // GET EXECUTIVE BY ID
    // ==========================================================

    /**
     * Returns one Executive by ID.
     *
     * @param id Executive ID
     * @return Executive details
     */
    UserResponse getExecutiveById(Long id);

    // ==========================================================
    // UPDATE EXECUTIVE
    // ==========================================================

    /**
     * Updates Executive information.
     *
     * @param id Executive ID
     * @param request updated Executive information
     * @param adminId ID of Admin performing the update
     * @return updated Executive
     */
    UserResponse updateExecutive(
            Long id,
            com.leadpilot.crm.dto.UserManagementRequest request,
            Long adminId
    );

    // ==========================================================
    // ACTIVATE EXECUTIVE
    // ==========================================================

    /**
     * Activates an Executive account.
     *
     * @param id Executive ID
     * @param adminId ID of Admin performing the action
     * @return updated Executive
     */
    UserResponse activateExecutive(
            Long id,
            Long adminId
    );

    // ==========================================================
    // DEACTIVATE EXECUTIVE
    // ==========================================================

    /**
     * Deactivates an Executive account.
     *
     * @param id Executive ID
     * @param adminId ID of Admin performing the action
     * @return updated Executive
     */
    UserResponse deactivateExecutive(
            Long id,
            Long adminId
    );
}