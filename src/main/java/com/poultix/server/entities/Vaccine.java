package com.poultix.server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String name;          
    private String type;          
    private String targetDisease; 
    private String dosage;        
    private String administration; 
    private String storage;       
    private boolean prescriptionRequired;
}
