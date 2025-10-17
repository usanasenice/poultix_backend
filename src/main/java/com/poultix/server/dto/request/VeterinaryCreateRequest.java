package com.poultix.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinaryCreateRequest {
    private Double serviceRadius;
}
