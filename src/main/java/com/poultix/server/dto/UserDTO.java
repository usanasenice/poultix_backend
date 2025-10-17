package com.poultix.server.dto;

import com.poultix.server.entities.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String avatar;
    private LocationDTO location;
    private UserRole role;
    private Boolean isActive;
    private Boolean emailVerified;
    private Boolean recoverMode;
    private String privateKey;
    private String publicKey;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
