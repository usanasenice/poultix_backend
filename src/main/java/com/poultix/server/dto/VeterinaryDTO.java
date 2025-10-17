package com.poultix.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinaryDTO {
    private UUID id;
    private UserDTO user;
    private Double serviceRadius;
    private VeterinaryRatesDTO rates;
    private Double rating;
    private Integer totalVisits;
    private LocalDateTime joinDate;
    private Boolean isActive;
    private List<String> farmManaged;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
