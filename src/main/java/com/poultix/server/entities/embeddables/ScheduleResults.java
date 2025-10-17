package com.poultix.server.entities.embeddables;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ScheduleResults {
    private String findings;
    
    @ElementCollection
    private List<String> recommendations = new ArrayList<>();
    
    private Boolean followUpRequired = false;
    private LocalDate followUpDate;
    
    @ElementCollection
    private List<Medication> medications = new ArrayList<>();
}
