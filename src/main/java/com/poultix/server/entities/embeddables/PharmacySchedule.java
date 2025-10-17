package com.poultix.server.entities.embeddables;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Data;

@Embeddable
@Data
public class PharmacySchedule {
    private String day;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
