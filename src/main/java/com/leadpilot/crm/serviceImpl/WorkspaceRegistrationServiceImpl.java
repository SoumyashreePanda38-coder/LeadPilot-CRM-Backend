package com.leadpilot.crm.serviceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.dto.WorkspaceRegistrationRequest;
import com.leadpilot.crm.dto.WorkspaceRegistrationResponse;
import com.leadpilot.crm.entity.Organization;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.Role;
import com.leadpilot.crm.enums.UserStatus;
import com.leadpilot.crm.repository.OrganizationRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.WorkspaceRegistrationService;

/**
 * ==========================================================
 * Service Implementation :
 * WorkspaceRegistrationServiceImpl
 *
 * Description :
 * Handles the complete business logic for creating a new
 * LeadPilot CRM workspace and its first Admin user.
 *
 * ==========================================================
 */

@Service
public class WorkspaceRegistrationServiceImpl
        implements WorkspaceRegistrationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // ==========================================================
    // Constructor
    // ==========================================================

    public WorkspaceRegistrationServiceImpl(
            OrganizationRepository organizationRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // ==========================================================
    // Register Workspace
    // ==========================================================

    @Override
    @Transactional
    public WorkspaceRegistrationResponse registerWorkspace(
            WorkspaceRegistrationRequest request
    ) {

        // ------------------------------------------------------
        // 1. Validate Password
        // ------------------------------------------------------

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException(
                    "Password and confirm password do not match"
            );
        }


        // ------------------------------------------------------
        // 2. Check Username
        // ------------------------------------------------------

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException(
                    "Username already exists"
            );
        }


        // ------------------------------------------------------
        // 3. Check Email
        // ------------------------------------------------------

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already exists"
            );
        }


        // ------------------------------------------------------
        // 4. Create Organization
        // ------------------------------------------------------

        Organization organization = new Organization();

        organization.setOrganizationName(
                request.getOrganizationName().trim()
        );

        organization = organizationRepository.save(organization);


        // ------------------------------------------------------
        // 5. Create First Admin User
        // ------------------------------------------------------

        User admin = new User();

        admin.setFullName(
                request.getFullName().trim()
        );

        admin.setUsername(
                request.getUsername().trim()
        );

        admin.setEmail(
                request.getEmail().trim()
        );

        admin.setPhoneNumber(
                request.getPhoneNumber().trim()
        );

        admin.setDesignation(
                request.getDesignation()
        );


        // ------------------------------------------------------
        // 6. Generate Employee ID
        // ------------------------------------------------------

        admin.setEmployeeId(
                generateEmployeeId()
        );


        // ------------------------------------------------------
        // 7. Automatically Assign ADMIN Role
        // ------------------------------------------------------

        admin.setRole(Role.ADMIN);


        // ------------------------------------------------------
        // 8. Automatically Activate Admin
        // ------------------------------------------------------

        admin.setStatus(UserStatus.ACTIVE);


        // ------------------------------------------------------
        // 9. Encode Password
        // ------------------------------------------------------

        admin.setPassword(
                passwordEncoder.encode(request.getPassword())
        );


        // ------------------------------------------------------
        // 10. Link Admin to Organization
        // ------------------------------------------------------

        admin.setOrganization(organization);


        // ------------------------------------------------------
        // 11. Save Admin
        // ------------------------------------------------------

        admin = userRepository.save(admin);


        // ------------------------------------------------------
        // 12. Build Response
        // ------------------------------------------------------

        WorkspaceRegistrationResponse response =
                new WorkspaceRegistrationResponse();

        response.setOrganizationId(
                organization.getId()
        );

        response.setOrganizationName(
                organization.getOrganizationName()
        );

        response.setAdminId(
                admin.getId()
        );

        response.setEmployeeId(
                admin.getEmployeeId()
        );

        response.setFullName(
                admin.getFullName()
        );

        response.setUsername(
                admin.getUsername()
        );

        response.setEmail(
                admin.getEmail()
        );

        response.setPhoneNumber(
                admin.getPhoneNumber()
        );

        response.setDesignation(
                admin.getDesignation()
        );

        response.setRole(
                admin.getRole().name()
        );

        response.setMessage(
                "Workspace registered successfully"
        );

        return response;
    }


    // ==========================================================
    // Generate Employee ID
    // ==========================================================

    private String generateEmployeeId() {

        long nextNumber = userRepository.count() + 1;

        String employeeId;

        do {
            employeeId = String.format(
                    "ADM-%04d",
                    nextNumber
            );

            nextNumber++;

        } while (userRepository.existsByEmployeeId(employeeId));

        return employeeId;
    }
}