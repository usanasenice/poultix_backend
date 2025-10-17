package com.poultix.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivestockDTO {
    private Integer total;
    private Integer healthy;
    private Integer sick;
    private Integer atRisk;
    private List<String> breeds;
}
