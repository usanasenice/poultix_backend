package com.poultix.server.dto;

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
public class GoogleUserDTO {
    private UUID id;
    private String googleId;
    private String names;
    private String email;
    private String picture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
