package com.poultix.server.service;

import com.poultix.server.dto.UserDTO;
import com.poultix.server.dto.request.UserRegistrationRequest;
import com.poultix.server.dto.request.UserUpdateRequest;
import com.poultix.server.entities.User;
import com.poultix.server.entities.enums.UserRole;
import com.poultix.server.mappers.UserMapper;
import com.poultix.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Register a new user
     */
    public UserDTO registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAvatar(request.getAvatar());
        user.setRole(request.getRole());
        
        // Handle location - for now set to null, can be enhanced later
        user.setLocation(null);
        
        // Generate cryptographic keys (implement key generation logic as needed)
        user.setPrivateKey(generatePrivateKey());
        user.setPublicKey(generatePublicKey());
        
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
    
    /**
     * Get user by ID
     */
    @Transactional(readOnly = true)
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }
    
    /**
     * Get user by email
     */
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return userMapper.toDTO(user);
    }


    /**
     * Get user by email
     */
    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);    
        return user.isPresent();
    }

    
    
    /**
     * Get all users
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get users by role
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get active users
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getActiveUsers() {
        return userRepository.findByIsActive(true).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update user
     */
    public UserDTO updateUser(UUID id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        
        // Update user fields manually
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
            
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }
    
    /**
     * Deactivate user
     */
    public void deactivateUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        user.setIsActive(false);
        userRepository.save(user);
    }
    
    /**
     * Activate user
     */
    public void activateUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        user.setIsActive(true);
        userRepository.save(user);
    }
    
    /**
     * Delete user
     */
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    /**
     * Change password
     */
    public void changePassword(UUID id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    /**
     * Enable recovery mode
     */
    public void enableRecoveryMode(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        user.setIsRecoverMode(true);
        userRepository.save(user);
    }
    
    // Helper methods for key generation (implement based on your crypto requirements)
    private String generatePrivateKey() {
        // TODO: Implement proper private key generation
        return UUID.randomUUID().toString();
    }
    
    private String generatePublicKey() {
        // TODO: Implement proper public key generation
        return UUID.randomUUID().toString();
    }
}
