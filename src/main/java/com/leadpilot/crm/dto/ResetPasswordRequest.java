package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : ResetPasswordRequest
 *
 * Description :
 * Request used by Admin to reset the password of
 * a particular user.
 * ==========================================================
 */
public class ResetPasswordRequest {

    // ==========================================================
    // New Password
    // ==========================================================

    private String newPassword;

    // ==========================================================
    // Constructors
    // ==========================================================

    public ResetPasswordRequest() {
    }

    // ==========================================================
    // Getter & Setter
    // ==========================================================

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}