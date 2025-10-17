package com.poultix.server.dto;

import com.poultix.server.entities.enums.SchedulePriority;
import com.poultix.server.entities.enums.ScheduleStatus;
import com.poultix.server.entities.enums.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {
    private UUID id;
    private String title;
    private String description;
    private ScheduleType type;
    private UserDTO farmer;
    private UserDTO veterinary;
    private LocalDate scheduledDate;
    private ScheduleStatus status;
    private SchedulePriority priority;
    private UserDTO createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
