package com.poultix.server.service;

import com.poultix.server.dto.AuthResponse;
import com.poultix.server.dto.request.*;
import com.poultix.server.dto.UserDTO;
import com.poultix.server.entities.User;
import com.poultix.server.exceptions.*;
import com.poultix.server.mappers.UserMapper;
import com.poultix.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    
    public AuthResponse registerAndLogin(UserRegistrationRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAvatar(request.getAvatar());
        user.setRole(request.getRole());
        
        // Handle location - for now set to null, can be enhanced later
        // Note: The request has String location but entity expects Location object
        user.setLocation(request.getLocation());
        
        // Generate cryptographic keys (implement key generation logic as needed)
        user.setPrivateKey(generatePrivateKey());
        user.setPublicKey(generatePublicKey());
        
        User savedUser = userRepository.save(user);
        
        // Generate tokens for immediate login
        String accessToken = jwtService.generateAccessToken(savedUser.getEmail(), savedUser.getId());
        String refreshToken = jwtService.generateRefreshToken(savedUser.getEmail(), savedUser.getId());
        
        // Convert to response
        UserDTO userResponse = userMapper.toDTO(savedUser);
        
        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiresAt(LocalDateTime.now().plusDays(1))
            .user(userResponse)
            .build();
    }
    
    public AuthResponse login(UserLoginRequest request) {
        // Find user first
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException());
        
        // Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        
        // Optional: Check if email is verified
        // if (user.getEmailVerified() != null && !user.getEmailVerified()) {
        //     throw new EmailNotVerifiedException("Please verify your email before logging in");
        // }
            
        // Generate tokens
        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getId());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail(), user.getId());
        
        // Convert to response
        UserDTO userResponse = userMapper.toDTO(user);
        
        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiresAt(LocalDateTime.now().plusDays(1)) // Access token expires in 1 day
            .user(userResponse)
            .build();
    }
    
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String email = jwtService.getUsernameFromToken(request.getRefreshToken());
        
        if (email != null) {
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
                
            // Validate refresh token
            if (jwtService.isTokenValid(request.getRefreshToken(), email)) {
                String newAccessToken = jwtService.generateAccessToken(user.getEmail(), user.getId());
                String newRefreshToken = jwtService.generateRefreshToken(user.getEmail(), user.getId());
                
                UserDTO userResponse = userMapper.toDTO(user);
                
                return AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .expiresAt(LocalDateTime.now().plusDays(1))
                    .user(userResponse)
                    .build();
            }
        }
        
        throw new InvalidTokenException("Invalid refresh token");
    }
    
    public void logout(String token) {
        // In a real implementation, you would blacklist the token
        // For now, we'll just validate it exists
        try {
            jwtService.validateToken(token);
            // Token blacklisting logic would go here
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }
    }
    
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException());
            
        // Generate password reset token
        String resetToken = jwtService.generatePasswordResetToken(user.getEmail());
        
        // In a real implementation, you would:
        // 1. Save the token to database with expiration
        // 2. Send email with reset link containing the token
        
        // For now, just log it (in production, send email)
    }
    
    public void resetPassword(PasswordResetRequest request) {
        try {
            String email = jwtService.getUsernameFromToken(request.getCode());
            
            if (email != null) {
                User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException());
                    
                // Update password
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
                
                // In a real implementation, invalidate the reset token
            } else {
                throw new InvalidTokenException("Invalid or expired reset token");
            }
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid or expired reset token");
        }
    }
    
    public void verifyEmail(EmailVerificationRequest request) {
        try {
            String email = jwtService.getUsernameFromToken(request.getToken());
            
            if (email != null) {
                User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException());
                    
                // Mark email as verified
                user.setIsVerified(true);
                userRepository.save(user);
            } else {
                throw new InvalidTokenException("Invalid or expired verification token");
            }
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid or expired verification token");
        }
    } 
    
    public void sendEmailVerification(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException());
            
        // Generate email verification token
        String verificationToken = jwtService.generateEmailVerificationToken(user.getEmail());
        
        // In a real implementation, send email with verification link
    }
    
    // Helper methods for key generation (same as UserService)
    private String generatePrivateKey() {
        // TODO: Implement proper private key generation
        return UUID.randomUUID().toString();
    }
    
    private String generatePublicKey() {
        // TODO: Implement proper public key generation
        return UUID.randomUUID().toString();
    }
}
