package com.poultix.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineCreateRequest {
    @NotBlank(message = "Vaccine name is required")
    private String name;
    
    @NotBlank(message = "Vaccine type is required")
    private String type;
    
    @NotBlank(message = "Target disease is required")
    private String targetDisease;
    
    @NotBlank(message = "Dosage is required")
    private String dosage;
    
    @NotBlank(message = "Administration method is required")
    private String administration;
    
    @NotBlank(message = "Storage information is required")
    private String storage;
    
    @NotNull(message = "Prescription requirement must be specified")
    private Boolean prescriptionRequired;
}
