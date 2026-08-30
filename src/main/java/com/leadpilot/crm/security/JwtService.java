package com.leadpilot.crm.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
/**
 * ==========================================================
 * Service : JwtService
 *
 * Description :
 * Handles JWT generation, validation and extraction.
 *
 * ==========================================================
 */

@Service
public class JwtService {

    /**
     * Secret Key
     */
    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Token Expiration Time (Milliseconds)
     */
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * ==========================================================
     * Generate JWT Token
     * ==========================================================
     */
    public String generateToken(String username, String role) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * ==========================================================
     * Extract Username
     * ==========================================================
     */
    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    /**
     * ==========================================================
     * Validate Token
     * ==========================================================
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /**
     * ==========================================================
     * Check Token Expiry
     * ==========================================================
     */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    /**
     * ==========================================================
     * Extract Expiration Date
     * ==========================================================
     */
    private Date extractExpiration(String token) {

        return extractAllClaims(token).getExpiration();
    }

    /**
     * ==========================================================
     * Extract All Claims
     * ==========================================================
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * ==========================================================
     * Generate Signing Key
     * ==========================================================
     */
    private Key getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}