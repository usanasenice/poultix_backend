package com.poultix.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.poultix.server.entities.enums.PharmacyType;
import com.poultix.server.entities.enums.OwnershipType;
import com.poultix.server.entities.enums.OwnershipStatus;
import com.poultix.server.entities.enums.VerificationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmacyDTO {
    private UUID id;
    private String name;
    private String address;
    private PharmacyScheduleDTO schedule;
    private String phone;
    private Boolean isOpen;
    private CoordinatesDTO location;
    private Double rating;
    private List<VaccineDTO> vaccines;
    
    // Rwanda-specific Business Information
    private String licenseNumber;
    private LocalDate registrationDate;
    private String tinNumber;
    private PharmacyType pharmacyType;
    private OwnershipType ownershipType;
    
    // Rwanda Location Information
    private String province;
    private String district;
    private String sector;
    private String cell;
    private String village;
    
    // Responsible Pharmacist
    // Pharmacist Information (simplified)
    private String pharmacistName;
    private String pharmacistLicenseNumber;
    private String pharmacistPhone;
    private String pharmacistEmail;
    
    // Owner information
    private UUID ownerId;
    private String ownerName;
    
    // Premises Information
    private OwnershipStatus ownershipStatus;
    private String premisesSize;
    private String storageFacilities;
    
    // Document Paths
    private String businessLicensePath;
    private String pharmacistLicensePath;
    private String premisesInspectionPath;
    private String taxClearancePath;
    private String insuranceCertificatePath;
    private String complianceCertificatePath;
    
    // Verification Management
    private VerificationStatus verificationStatus;
    private List<String> missingDocuments;
    private String adminComments;
    private String lastReviewedBy;
    private LocalDateTime lastReviewedAt;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
