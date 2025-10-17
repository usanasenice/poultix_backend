package com.poultix.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccineDTO {
    private String id;
    private String name;
    private String type;
    private String targetDisease;
    private String dosage;
    private String administration;
    private String storage;
    private Boolean prescriptionRequired;
}
