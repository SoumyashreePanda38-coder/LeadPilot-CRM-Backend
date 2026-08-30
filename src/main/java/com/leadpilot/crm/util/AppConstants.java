package com.leadpilot.crm.util;

/**
 * Application-wide constants used throughout LeadPilot CRM.
 * This class should only contain application constants,
 * not business enums like Role or LeadStatus.
 */
public final class AppConstants {

    // Prevent object creation
    private AppConstants() {
        throw new IllegalStateException("Utility class");
    }

    /* ==========================================================
                       APPLICATION INFORMATION
       ========================================================== */

    public static final String APPLICATION_NAME = "LeadPilot CRM";
    public static final String APPLICATION_VERSION = "1.0.0";



    /* ==========================================================
                           API CONFIGURATION
       ========================================================== */

    public static final String API_BASE_URL = "/api";
    public static final String API_VERSION = "/v1";



    /* ==========================================================
                         DEFAULT APPLICATION VALUES
       ========================================================== */

    public static final String DEFAULT_PASSWORD = "Password@123";
    public static final String DEFAULT_PROFILE_IMAGE = "default-profile.png";



    /* ==========================================================
                           DATE & TIME FORMAT
       ========================================================== */

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";



    /* ==========================================================
                          SUCCESS MESSAGES
       ========================================================== */

    public static final String LOGIN_SUCCESS = "Login successful.";

    public static final String LOGOUT_SUCCESS = "Logout successful.";

    public static final String USER_CREATED_SUCCESS =
            "User created successfully.";

    public static final String USER_UPDATED_SUCCESS =
            "User updated successfully.";

    public static final String USER_DELETED_SUCCESS =
            "User deleted successfully.";

    public static final String PASSWORD_CHANGED_SUCCESS =
            "Password changed successfully.";

    public static final String LEAD_CREATED_SUCCESS =
            "Lead created successfully.";

    public static final String LEAD_UPDATED_SUCCESS =
            "Lead updated successfully.";

    public static final String LEAD_DELETED_SUCCESS =
            "Lead deleted successfully.";

    public static final String FOLLOWUP_ADDED_SUCCESS =
            "Follow-up added successfully.";

    public static final String NOTE_ADDED_SUCCESS =
            "Note added successfully.";

    public static final String REMINDER_UPDATED_SUCCESS =
            "Reminder updated successfully.";




    /* ==========================================================
                           ERROR MESSAGES
       ========================================================== */

    public static final String INVALID_CREDENTIALS =
            "Invalid username or password.";

    public static final String USER_NOT_FOUND =
            "User not found.";

    public static final String RECORD_NOT_FOUND =
            "Requested record not found.";

    public static final String DUPLICATE_RECORD =
            "Record already exists.";

    public static final String ACCESS_DENIED =
            "Access denied.";

    public static final String INTERNAL_SERVER_ERROR =
            "Something went wrong. Please try again later.";



    /* ==========================================================
                           PAGINATION
       ========================================================== */

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;



    /* ==========================================================
                          FILE UPLOAD
       ========================================================== */

    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    public static final String IMAGE_UPLOAD_DIRECTORY =
            "uploads/profile-images/";
}