package com.leadpilot.crm.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.dto.UserManagementRequest;
import com.leadpilot.crm.dto.UserResponse;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.Role;
import com.leadpilot.crm.enums.UserStatus;
import com.leadpilot.crm.mapper.UserMapper;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.UserManagementService;

/**
 * ==========================================================
 * Service Implementation : UserManagementServiceImpl
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

@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

    // ==========================================================
    // DEPENDENCIES
    // ==========================================================

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    public UserManagementServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    // ==========================================================
    // CREATE EXECUTIVE
    // ==========================================================

    @Override
    public UserResponse createExecutive(
            UserManagementRequest request,
            Long adminId) {

        // ------------------------------------------------------
        // Validate Admin
        // ------------------------------------------------------

        User admin = getAdmin(adminId);


        // ------------------------------------------------------
        // Validate Request
        // ------------------------------------------------------

        if (request == null) {
            throw new IllegalArgumentException(
                    "User request cannot be null"
            );
        }


        // ------------------------------------------------------
        // Check Employee ID
        // ------------------------------------------------------

        if (userRepository.existsByEmployeeId(
                request.getEmployeeId())) {

            throw new IllegalArgumentException(
                    "Employee ID already exists"
            );
        }


        // ------------------------------------------------------
        // Check Username
        // ------------------------------------------------------

        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new IllegalArgumentException(
                    "Username already exists"
            );
        }


        // ------------------------------------------------------
        // Check Email
        // ------------------------------------------------------

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new IllegalArgumentException(
                    "Email already exists"
            );
        }


        // ------------------------------------------------------
        // Create Executive
        // ------------------------------------------------------

        User executive = new User();

        executive.setEmployeeId(
                request.getEmployeeId()
        );

        executive.setFullName(
                request.getFullName()
        );

        executive.setUsername(
                request.getUsername()
        );

        executive.setEmail(
                request.getEmail()
        );

        executive.setPhoneNumber(
                request.getPhoneNumber()
        );


        // ------------------------------------------------------
        // Encode Password
        // ------------------------------------------------------

        executive.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        // ------------------------------------------------------
        // Automatically assign EXECUTIVE role
        //
        // Role is NOT accepted from frontend.
        // ------------------------------------------------------

        executive.setRole(
                Role.EXECUTIVE
        );


        // ------------------------------------------------------
        // Other Information
        // ------------------------------------------------------

        executive.setDesignation(
                request.getDesignation()
        );

        executive.setDepartment(
                request.getDepartment()
        );


        // ------------------------------------------------------
        // Default Status
        // ------------------------------------------------------

        executive.setStatus(
                UserStatus.ACTIVE
        );


        // ------------------------------------------------------
        // Audit Information
        // ------------------------------------------------------

        executive.setCreatedBy(admin);

        executive.setUpdatedBy(admin);


        // ------------------------------------------------------
        // Save Executive
        // ------------------------------------------------------

        User savedExecutive =
                userRepository.save(executive);


        // ------------------------------------------------------
        // Convert Entity → Response DTO
        // ------------------------------------------------------

        return userMapper.toUserResponse(
                savedExecutive
        );
    }


    // ==========================================================
    // GET ALL EXECUTIVES
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllExecutives() {

        List<User> executives =
                userRepository.findByRole(
                        Role.EXECUTIVE
                );

        return executives.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }


    // ==========================================================
    // GET EXECUTIVE BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public UserResponse getExecutiveById(
            Long id) {

        User executive =
                getExecutive(id);

        return userMapper.toUserResponse(
                executive
        );
    }


    // ==========================================================
    // UPDATE EXECUTIVE
    // ==========================================================

    @Override
    public UserResponse updateExecutive(
            Long id,
            UserManagementRequest request,
            Long adminId) {

        // ------------------------------------------------------
        // Validate Admin
        // ------------------------------------------------------

        User admin = getAdmin(adminId);


        // ------------------------------------------------------
        // Find Executive
        // ------------------------------------------------------

        User executive =
                getExecutive(id);


        // ------------------------------------------------------
        // Validate Request
        // ------------------------------------------------------

        if (request == null) {
            throw new IllegalArgumentException(
                    "User request cannot be null"
            );
        }


        // ------------------------------------------------------
        // Employee ID
        // ------------------------------------------------------

        if (!executive.getEmployeeId()
                .equals(request.getEmployeeId())) {

            if (userRepository.existsByEmployeeId(
                    request.getEmployeeId())) {

                throw new IllegalArgumentException(
                        "Employee ID already exists"
                );
            }

            executive.setEmployeeId(
                    request.getEmployeeId()
            );
        }


        // ------------------------------------------------------
        // Username
        // ------------------------------------------------------

        if (!executive.getUsername()
                .equals(request.getUsername())) {

            if (userRepository.existsByUsername(
                    request.getUsername())) {

                throw new IllegalArgumentException(
                        "Username already exists"
                );
            }

            executive.setUsername(
                    request.getUsername()
            );
        }


        // ------------------------------------------------------
        // Email
        // ------------------------------------------------------

        if (!executive.getEmail()
                .equals(request.getEmail())) {

            if (userRepository.existsByEmail(
                    request.getEmail())) {

                throw new IllegalArgumentException(
                        "Email already exists"
                );
            }

            executive.setEmail(
                    request.getEmail()
            );
        }


        // ------------------------------------------------------
        // Update Basic Information
        // ------------------------------------------------------

        executive.setFullName(
                request.getFullName()
        );

        executive.setPhoneNumber(
                request.getPhoneNumber()
        );

        executive.setDesignation(
                request.getDesignation()
        );

        executive.setDepartment(
                request.getDepartment()
        );


        // ------------------------------------------------------
        // Update Password
        // ------------------------------------------------------

        executive.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        // ------------------------------------------------------
        // IMPORTANT
        //
        // Always remain EXECUTIVE.
        // Admin cannot change role through this API.
        // ------------------------------------------------------

        executive.setRole(
                Role.EXECUTIVE
        );


        // ------------------------------------------------------
        // Audit
        // ------------------------------------------------------

        executive.setUpdatedBy(admin);


        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        User updatedExecutive =
                userRepository.save(executive);


        // ------------------------------------------------------
        // Return Response
        // ------------------------------------------------------

        return userMapper.toUserResponse(
                updatedExecutive
        );
    }


    // ==========================================================
    // ACTIVATE EXECUTIVE
    // ==========================================================

    @Override
    public UserResponse activateExecutive(
            Long id,
            Long adminId) {

        // ------------------------------------------------------
        // Validate Admin
        // ------------------------------------------------------

        User admin = getAdmin(adminId);


        // ------------------------------------------------------
        // Find Executive
        // ------------------------------------------------------

        User executive =
                getExecutive(id);


        // ------------------------------------------------------
        // Activate
        // ------------------------------------------------------

        executive.setStatus(
                UserStatus.ACTIVE
        );


        // ------------------------------------------------------
        // Audit
        // ------------------------------------------------------

        executive.setUpdatedBy(admin);


        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        User updatedExecutive =
                userRepository.save(executive);


        return userMapper.toUserResponse(
                updatedExecutive
        );
    }


    // ==========================================================
    // DEACTIVATE EXECUTIVE
    // ==========================================================

    @Override
    public UserResponse deactivateExecutive(
            Long id,
            Long adminId) {

        // ------------------------------------------------------
        // Validate Admin
        // ------------------------------------------------------

        User admin = getAdmin(adminId);


        // ------------------------------------------------------
        // Find Executive
        // ------------------------------------------------------

        User executive =
                getExecutive(id);


        // ------------------------------------------------------
        // Deactivate
        // ------------------------------------------------------

        executive.setStatus(
                UserStatus.INACTIVE
        );


        // ------------------------------------------------------
        // Audit
        // ------------------------------------------------------

        executive.setUpdatedBy(admin);


        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        User updatedExecutive =
                userRepository.save(executive);


        return userMapper.toUserResponse(
                updatedExecutive
        );
    }


    // ==========================================================
    // PRIVATE METHOD
    // GET ADMIN
    // ==========================================================

    private User getAdmin(Long adminId) {

        if (adminId == null) {
            throw new IllegalArgumentException(
                    "Admin ID cannot be null"
            );
        }

        User admin =
                userRepository.findById(adminId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Admin not found with ID: "
                                                + adminId
                                )
                        );


        // ------------------------------------------------------
        // Make sure supplied user is actually ADMIN
        // ------------------------------------------------------

        if (admin.getRole() != Role.ADMIN) {

            throw new IllegalArgumentException(
                    "Only ADMIN can perform this operation"
            );
        }

        return admin;
    }


    // ==========================================================
    // PRIVATE METHOD
    // GET EXECUTIVE
    // ==========================================================

    private User getExecutive(Long id) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "Executive ID cannot be null"
            );
        }

        User executive =
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Executive not found with ID: "
                                                + id
                                )
                        );


        // ------------------------------------------------------
        // Make sure user is EXECUTIVE
        // ------------------------------------------------------

        if (executive.getRole() != Role.EXECUTIVE) {

            throw new IllegalArgumentException(
                    "The specified user is not an Executive"
            );
        }

        return executive;
    }
}