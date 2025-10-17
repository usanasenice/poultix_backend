package com.poultix.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinaryRatesDTO {
    private Double consultation;
    private Double inspection;
    private Double emergency;
    private Double vaccination;
}
