package com.leadpilot.crm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.repository.UserRepository;

/**
 * ==========================================================
 * Service : CustomUserDetailsService
 *
 * Description :
 * Loads user information from the database and converts
 * it into CustomUserDetails for Spring Security.
 * ==========================================================
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * ==========================================================
     * Load user by username.
     * Called automatically by Spring Security during login.
     * ==========================================================
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with username : " + username));

        return new CustomUserDetails(user);
    }

}