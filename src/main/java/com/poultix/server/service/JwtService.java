package com.poultix.server.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.poultix.server.dto.JwtValidation;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService  {
    private final SecretKey SECRET_KEY;
    private final long ACCESS_TOKEN_EXPIRATION = 86400000; // 1 day in milliseconds
    private final long REFRESH_TOKEN_EXPIRATION = 604800000; // 7 days in milliseconds

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String generateAccessToken(String email, UUID userId) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + ACCESS_TOKEN_EXPIRATION;
        return Jwts.builder()
                .claim("sub", email)
                .claim("userId", userId.toString())
                .claim("type", "access")
                .claim("iat", new Date(nowMillis))
                .claim("exp", new Date(expMillis))
                .signWith(SECRET_KEY)
                .compact();
    }
    
    public String generateRefreshToken(String email, UUID userId) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + REFRESH_TOKEN_EXPIRATION;
        return Jwts.builder()
                .claim("sub", email)
                .claim("userId", userId.toString())
                .claim("type", "refresh")
                .claim("iat", new Date(nowMillis))
                .claim("exp", new Date(expMillis))
                .signWith(SECRET_KEY)
                .compact();
    }
    
    public String generatePasswordResetToken(String email) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 3600000; // 1 hour for password reset
        return Jwts.builder()
                .claim("sub", email)
                .claim("type", "password_reset")
                .claim("iat", new Date(nowMillis))
                .claim("exp", new Date(expMillis))
                .signWith(SECRET_KEY)
                .compact();
    }
    
    public String generateEmailVerificationToken(String email) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 86400000; // 24 hours for email verification
        return Jwts.builder()
                .claim("sub", email)
                .claim("type", "email_verification")
                .claim("iat", new Date(nowMillis))
                .claim("exp", new Date(expMillis))
                .signWith(SECRET_KEY)
                .compact();
    }


    public String getUsernameFromToken(String token) throws AccessDeniedException  {
        JwtValidation result = validateToken(token);
        return result.isValid() ? result.getUsername() : null;
    }
    
    public UUID getUserIdFromToken(String token) throws AccessDeniedException {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String userIdStr = claims.get("userId", String.class);
            return userIdStr != null ? UUID.fromString(userIdStr) : null;
        } catch (Exception e) {
            throw new AccessDeniedException("Invalid token");
        }
    }
    
    public String getTokenType(String token) throws AccessDeniedException {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("type", String.class);
        } catch (Exception e) {
            throw new AccessDeniedException("Invalid token");
        }
    }


    public boolean isTokenValid(String token, String username) throws AccessDeniedException  {
        String extractedUsername = getUsernameFromToken(token);
        return extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token);
    }
    
    public boolean isTokenExpired(String token) throws AccessDeniedException {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true; // Consider expired if we can't parse
        }
    }

  
    public JwtValidation validateToken(String token) throws AccessDeniedException {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String username = claims.get("sub", String.class);
            return new JwtValidation(true, username, null);
        } catch (Exception e) {
            throw new AccessDeniedException(e.getLocalizedMessage());
        }
    }
}