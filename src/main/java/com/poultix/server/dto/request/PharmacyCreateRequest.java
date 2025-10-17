package com.poultix.server.dto.request;

import com.poultix.server.entities.Vaccine;
import com.poultix.server.entities.embeddables.Coordinates;
import com.poultix.server.entities.embeddables.PharmacySchedule;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyCreateRequest {
    @NotBlank(message = "Pharmacy name is required")
    private String name;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "Phone is required")
    private String phone;
    
    private Coordinates location;
    private List<String> services;
    private List<Vaccine> vaccines;

    private PharmacySchedule schedule;
}
