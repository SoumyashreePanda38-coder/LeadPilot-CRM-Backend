package com.leadpilot.crm.mapper;

import org.springframework.stereotype.Component;

import com.leadpilot.crm.dto.LoginResponse;
import com.leadpilot.crm.dto.UserProfileResponse;
import com.leadpilot.crm.dto.UserResponse;
import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Mapper : UserMapper
 *
 * Description :
 * Converts User Entity into different DTOs used
 * throughout the application.
 * ==========================================================
 */

@Component
public class UserMapper {

    /**
     * ==========================================================
     * Convert User Entity to LoginResponse
     * ==========================================================
     */
    public LoginResponse toLoginResponse(User user) {

        if (user == null) {
            return null;
        }

        LoginResponse response = new LoginResponse();

        response.setId(user.getId());
        response.setEmployeeId(user.getEmployeeId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setDesignation(user.getDesignation());
        response.setProfileImage(user.getProfileImage());

        return response;
    }

    /**
     * ==========================================================
     * Convert User Entity to UserProfileResponse
     * ==========================================================
     */
    public UserProfileResponse toUserProfileResponse(User user) {

        if (user == null) {
            return null;
        }

        UserProfileResponse response = new UserProfileResponse();

        response.setId(user.getId());
        response.setEmployeeId(user.getEmployeeId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setDesignation(user.getDesignation());
        response.setStatus(user.getStatus());
        response.setProfileImage(user.getProfileImage());

        return response;
    }

    /**
     * ==========================================================
     * Convert User Entity to UserResponse
     * ==========================================================
     */
    public UserResponse toUserResponse(User user) {

        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmployeeId(user.getEmployeeId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setDesignation(user.getDesignation());
        response.setStatus(user.getStatus());
        response.setProfileImage(user.getProfileImage());
        response.setDepartment(user.getDepartment());
        response.setLastLogin(user.getLastLogin());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}