package com.poultix.server.entities.embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Facilities {
    private Integer coops = 0;
    private Boolean feedStorage = false;
    private String waterSystem;
    private Boolean electricityAccess = false;
}
