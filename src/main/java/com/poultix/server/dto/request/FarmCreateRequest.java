package com.poultix.server.dto.request;

import com.poultix.server.entities.embeddables.Facilities;
import com.poultix.server.entities.embeddables.Livestock;
import com.poultix.server.entities.embeddables.Location;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmCreateRequest {
    @NotBlank(message = "Farm name is required")
    private String name;
    private Location location;
    private Livestock livestock;
    private Facilities facilities;
}
