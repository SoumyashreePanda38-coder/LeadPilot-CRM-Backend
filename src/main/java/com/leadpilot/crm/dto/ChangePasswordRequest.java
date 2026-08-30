package com.leadpilot.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ==========================================================
 * DTO : ChangePasswordRequest
 *
 * Description :
 * Used to change the password of the currently logged-in user.
 * ==========================================================
 */

public class ChangePasswordRequest {

    @NotBlank(message = "Current password is required.")
    @Size(min = 6, max = 100,
            message = "Current password must be between 6 and 100 characters.")
    private String currentPassword;

    @NotBlank(message = "New password is required.")
    @Size(min = 6, max = 100,
            message = "New password must be between 6 and 100 characters.")
    private String newPassword;

    @NotBlank(message = "Confirm password is required.")
    @Size(min = 6, max = 100,
            message = "Confirm password must be between 6 and 100 characters.")
    private String confirmPassword;

    // ==========================================================
    // Constructors
    // ==========================================================

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String currentPassword,
                                 String newPassword,
                                 String confirmPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}