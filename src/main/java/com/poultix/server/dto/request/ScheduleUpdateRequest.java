package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.SchedulePriority;
import com.poultix.server.entities.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateRequest {
    private String title;
    private String description;
    private LocalDate scheduledDate;
    private ScheduleStatus status;
    private SchedulePriority priority;
}
