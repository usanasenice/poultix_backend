package com.poultix.server.dto;

import com.poultix.server.entities.enums.FarmStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmDTO {
    private UUID id;
    private String name;
    private UserDTO owner;
    private LocationDTO location;
    private Double size;
    private LocalDate establishedDate;
    private LivestockDTO livestock;
    private FacilitiesDTO facilities;
    private UserDTO assignedVeterinary;
    private FarmStatus healthStatus;
    private LocalDate lastInspection;
    private List<String> certifications;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
