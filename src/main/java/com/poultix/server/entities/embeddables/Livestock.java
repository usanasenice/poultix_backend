package com.poultix.server.entities.embeddables;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Livestock {
    private Integer total = 0;
    private Integer healthy = 0;
    private Integer sick = 0;
    private Integer atRisk = 0;
    
    @ElementCollection
    private List<String> breeds = new ArrayList<>();
}
