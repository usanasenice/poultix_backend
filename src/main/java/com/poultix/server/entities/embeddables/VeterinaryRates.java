package com.poultix.server.entities.embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class VeterinaryRates {
    private Double consultation = 0.0;
    private Double inspection = 0.0;
    private Double emergency = 0.0;
    private Double vaccination = 0.0;
}
