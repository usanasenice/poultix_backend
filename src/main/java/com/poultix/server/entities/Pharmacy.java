package com.poultix.server.entities;

import com.poultix.server.entities.embeddables.Coordinates;
import com.poultix.server.entities.embeddables.PharmacySchedule;
import com.poultix.server.entities.enums.PharmacyType;
import com.poultix.server.entities.enums.OwnershipType;
import com.poultix.server.entities.enums.OwnershipStatus;
import com.poultix.server.entities.enums.VerificationStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pharmacies")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    // Owner relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    
    @Column(nullable = true)
    private String name;
    
    @Column(nullable = true)
    private String address;
    
    @Embedded
    private PharmacySchedule schedule;
    
    
    @Column(nullable = true)
    private String phone;
    
    @Column(name = "is_open")
    private Boolean isOpen = true;
    
    @Embedded
    private Coordinates location;
    
    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Rating must not exceed 5")
    private Double rating;
    
    @ManyToMany
    @JoinTable(
        name = "pharmacy_medicines",
        joinColumns = @JoinColumn(name = "pharmacy_id"),
        inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<Vaccine> vaccines = new ArrayList<>();
    
    // Rwanda-specific Business Information
    @Column(name = "license_number")
    private String licenseNumber;
    
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    
    @Column(name = "tin_number")
    private String tinNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pharmacy_type")
    private PharmacyType pharmacyType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ownership_type")
    private OwnershipType ownershipType;
    
    // Rwanda Location Information
    @Column(name = "province")
    private String province;
    
    @Column(name = "district")
    private String district;
    
    @Column(name = "sector")
    private String sector;
    
    @Column(name = "cell")
    private String cell;
    
    @Column(name = "village")
    private String village;
    
    // Pharmacist Information (simplified)
    @Column(name = "pharmacist_name")
    private String pharmacistName;
    
    @Column(name = "pharmacist_license_number")
    private String pharmacistLicenseNumber;
    
    @Column(name = "pharmacist_phone")
    private String pharmacistPhone;
    
    @Column(name = "pharmacist_email")
    private String pharmacistEmail;
    
    // Premises Information
    @Enumerated(EnumType.STRING)
    @Column(name = "ownership_status")
    private OwnershipStatus ownershipStatus;
    
    @Column(name = "premises_size")
    private String premisesSize;
    
    @Column(name = "storage_facilities", columnDefinition = "TEXT")
    private String storageFacilities;
    
    // Document Paths
    @Column(name = "business_license_path")
    private String businessLicensePath;
    
    @Column(name = "pharmacist_license_path")
    private String pharmacistLicensePath;
    
    @Column(name = "premises_inspection_path")
    private String premisesInspectionPath;
    
    @Column(name = "tax_clearance_path")
    private String taxClearancePath;
    
    @Column(name = "insurance_certificate_path")
    private String insuranceCertificatePath;
    
    @Column(name = "compliance_certificate_path")
    private String complianceCertificatePath;
    
    // Verification Management
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.UNVERIFIED;
    
    @ElementCollection
    @CollectionTable(name = "pharmacy_missing_documents", joinColumns = @JoinColumn(name = "pharmacy_id"))
    @Column(name = "document_name")
    private List<String> missingDocuments;
    
    @Column(name = "admin_comments", columnDefinition = "TEXT")
    private String adminComments;
    
    @Column(name = "last_reviewed_by")
    private String lastReviewedBy;
    
    @Column(name = "last_reviewed_at")
    private LocalDateTime lastReviewedAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
