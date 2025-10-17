package com.poultix.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineUpdateRequest {
    private String name;
    private String type;
    private String targetDisease;
    private String dosage;
    private String administration;
    private String storage;
    private Boolean prescriptionRequired;
}
