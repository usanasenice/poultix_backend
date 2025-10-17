package com.poultix.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResultsDTO {
    private String findings;
    private List<String> recommendations;
    private Boolean followUpRequired;
    private LocalDate followUpDate;
    private List<MedicationDTO> medications;
}
