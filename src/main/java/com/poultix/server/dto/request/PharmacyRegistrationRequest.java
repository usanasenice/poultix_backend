package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.PharmacyType;
import com.poultix.server.entities.enums.OwnershipType;
import com.poultix.server.entities.enums.OwnershipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyRegistrationRequest {
    
    // Basic Information (existing fields)
    @NotBlank(message = "Pharmacy name is required")
    private String name;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "Phone is required")
    private String phone;
    
    // Rwanda-specific Business Information
    @NotBlank(message = "License number is required")
    private String licenseNumber;
    
    @NotNull(message = "Registration date is required")
    private LocalDate registrationDate;
    
    @NotBlank(message = "TIN number is required")
    private String tinNumber;
    
    @NotNull(message = "Pharmacy type is required")
    private PharmacyType pharmacyType;
    
    @NotNull(message = "Ownership type is required")
    private OwnershipType ownershipType;
    
    // Rwanda Location Information
    @NotBlank(message = "Province is required")
    private String province;
    
    @NotBlank(message = "District is required")
    private String district;
    
    @NotBlank(message = "Sector is required")
    private String sector;
    
    @NotBlank(message = "Cell is required")
    private String cell;
    
    @NotBlank(message = "Village is required")
    private String village;
    
    // GPS Coordinates
    private Double latitude;
    private Double longitude;
    
    // Optional Pharmacist Information (simplified)
    private String pharmacistName;
    private String pharmacistLicenseNumber;
    private String pharmacistPhone;
    private String pharmacistEmail;
    
    // Premises Information
    private OwnershipStatus ownershipStatus;
    private String premisesSize;
    private String storageFacilities;
}
