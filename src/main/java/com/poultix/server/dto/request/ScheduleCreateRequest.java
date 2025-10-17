package com.poultix.server.dto.request;

import com.poultix.server.dto.FarmDTO;
import com.poultix.server.dto.UserDTO;
import com.poultix.server.entities.enums.SchedulePriority;
import com.poultix.server.entities.enums.ScheduleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Schedule type is required")
    private ScheduleType type;

    @NotNull(message = "Veterinary ID is required")
    private UserDTO veterinary;

    @NotNull(message = "Scheduled date is required")
    private LocalDate scheduledDate;

    private SchedulePriority priority;

    private FarmDTO farm;
}
