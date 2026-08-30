package com.leadpilot.crm.service;

import com.leadpilot.crm.dto.ChangePasswordRequest;
import com.leadpilot.crm.dto.LoginRequest;
import com.leadpilot.crm.dto.LoginResponse;
import com.leadpilot.crm.dto.UserProfileResponse;

/**
 * ==========================================================
 * Service Interface : AuthService
 *
 * Description :
 * Defines all authentication-related operations
 * for LeadPilot CRM.
 * ==========================================================
 */
public interface AuthService {

    /**
     * ==========================================================
     * Authenticate user using username and password.
     *
     * @param loginRequest Login credentials
     * @return Logged-in user details
     * ==========================================================
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * ==========================================================
     * Logout the currently logged-in user.
     *
     * @return Success message
     * ==========================================================
     */
    String logout();

    /**
     * ==========================================================
     * Change password of the currently logged-in user.
     *
     * @param changePasswordRequest Password details
     * @return Success message
     * ==========================================================
     */
    String changePassword(ChangePasswordRequest changePasswordRequest);

    /**
     * ==========================================================
     * Get profile details of the currently logged-in user.
     *
     * @return User profile information
     * ==========================================================
     */
    UserProfileResponse getLoggedInUserProfile();

}