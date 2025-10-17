package com.poultix.server.util;

import com.poultix.server.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    
    private final JwtService jwtService;
    
    /**
     * Extract Bearer token from Authorization header
     */
    public String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
    
    /**
     * Validate token and return user email
     */
    public String validateTokenAndGetEmail(String token) throws AccessDeniedException {
        return jwtService.getUsernameFromToken(token);
    }
    
    /**
     * Validate token and return user ID
     */
    public UUID validateTokenAndGetUserId(String token) throws AccessDeniedException {
        return jwtService.getUserIdFromToken(token);
    }
    
    /**
     * Check if token is of specific type
     */
    public boolean isTokenOfType(String token, String expectedType) {
        try {
            String tokenType = jwtService.getTokenType(token);
            return expectedType.equals(tokenType);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Validate token for specific user
     */
    public boolean isTokenValidForUser(String token, String email) {
        try {
            return jwtService.isTokenValid(token, email);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        try {
            return jwtService.isTokenExpired(token);
        } catch (Exception e) {
            return true; // Consider expired if we can't parse
        }
    }
    
    /**
     * Validate access token specifically
     */
    public boolean isValidAccessToken(String token) {
        return isTokenOfType(token, "access") && !isTokenExpired(token);
    }
    
    /**
     * Validate refresh token specifically
     */
    public boolean isValidRefreshToken(String token) {
        return isTokenOfType(token, "refresh") && !isTokenExpired(token);
    }
}
