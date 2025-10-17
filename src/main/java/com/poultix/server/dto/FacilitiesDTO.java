package com.poultix.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilitiesDTO {
    private Integer coops;
    private Boolean feedStorage;
    private String waterSystem;
    private Boolean electricityAccess;
}
