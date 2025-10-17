package com.poultix.server.entities;

import com.poultix.server.entities.embeddables.Location;
import com.poultix.server.entities.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotNull(message = "Name is required")
    @Column(nullable = false)
    private String name;
    
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotNull(message = "Password is required")
    @Column(nullable = false)
    private String password;
    
    private String avatar;

    @NotNull(message = "Location is required")
    @Embedded
    private Location location;
    
    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "email_verified")
    private Boolean isVerified = false;
    
    @Column(name = "recover_mode")
    private Boolean isRecoverMode = false;
    
    @NotNull(message = "Private key is required")
    @Column(nullable = false, name = "private_key")
    private String privateKey;
    
    @NotNull(message = "Public key is required")
    @Column(nullable = false, name = "public_key")
    private String publicKey;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
