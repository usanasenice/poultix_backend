package com.poultix.server.entities.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Location {
    private Double latitude;
    private Double longitude;
}
