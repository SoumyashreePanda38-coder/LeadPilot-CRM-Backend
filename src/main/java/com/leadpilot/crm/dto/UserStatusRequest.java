package com.leadpilot.crm.dto;

import com.leadpilot.crm.enums.UserStatus;

/**
 * ==========================================================
 * DTO : UserStatusRequest
 *
 * Description :
 * Request used by Admin to activate or deactivate
 * a user account.
 * ==========================================================
 */
public class UserStatusRequest {

    // ==========================================================
    // User Status
    // ==========================================================

    private UserStatus status;

    // ==========================================================
    // Constructors
    // ==========================================================

    public UserStatusRequest() {
    }

    // ==========================================================
    // Getter & Setter
    // ==========================================================

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}