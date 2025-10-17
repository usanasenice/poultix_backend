package com.poultix.server.dto.request;

import com.poultix.server.dto.VaccineDTO;
import com.poultix.server.entities.embeddables.Coordinates;
import com.poultix.server.entities.embeddables.PharmacySchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyUpdateRequest {
    private String name;
    private String address;
    private String phone;
    private Coordinates location;
    private List<String> services;
    private List<VaccineDTO> vaccines;
    private PharmacySchedule schedule;
}
