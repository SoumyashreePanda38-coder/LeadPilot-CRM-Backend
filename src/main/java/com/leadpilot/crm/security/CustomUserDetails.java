package com.leadpilot.crm.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Class : CustomUserDetails
 *
 * Description :
 * Converts our User entity into a Spring Security
 * UserDetails object.
 * ==========================================================
 */

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Return complete User entity whenever required.
     */
    public User getUser() {
        return user;
    }

    /**
     * User Role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        );
    }

    /**
     * Password stored in database
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Username used for login
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Account expired?
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Account locked?
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Password expired?
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * User enabled?
     *
     * Only ACTIVE users can login.
     */
    @Override
    public boolean isEnabled() {
        return user.getStatus().name().equals("ACTIVE");
    }

}