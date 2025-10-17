package com.poultix.server.service;

import com.poultix.server.dto.request.PharmacyCreateRequest;
import com.poultix.server.dto.request.PharmacyUpdateRequest;
import com.poultix.server.dto.request.PharmacyRegistrationRequest;
import com.poultix.server.dto.request.PharmacyVerificationRequest;
import com.poultix.server.dto.PharmacyDTO;
import com.poultix.server.entities.Pharmacy;
import com.poultix.server.entities.User;
import com.poultix.server.entities.embeddables.Coordinates;
import com.poultix.server.entities.enums.VerificationStatus;
import com.poultix.server.mappers.PharmacyMapper;
import com.poultix.server.repository.PharmacyRepository;
import com.poultix.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyMapper pharmacyMapper;
    private final UserRepository userRepository;

    public PharmacyDTO createPharmacy(PharmacyCreateRequest request) {
        // Create new pharmacy
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName(request.getName());
        pharmacy.setAddress(request.getAddress());
        pharmacy.setPhone(request.getPhone());
        pharmacy.setLocation(request.getLocation());
        pharmacy.setVaccines(request.getVaccines());

        Pharmacy savedPharmacy = pharmacyRepository.save(pharmacy);
        return pharmacyMapper.toDTO(savedPharmacy);
    }

    @Transactional(readOnly = true)
    public PharmacyDTO getPharmacyById(UUID id) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));
        return pharmacyMapper.toDTO(pharmacy);
    }

    @Transactional(readOnly = true)
    public List<PharmacyDTO> getAllPharmacies() {
        return pharmacyRepository.findAll().stream()
                .map(pharmacyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PharmacyDTO updatePharmacy(UUID id, PharmacyUpdateRequest request) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));

        // Update pharmacy fields manually
        if (request.getName() != null) {
            pharmacy.setName(request.getName());
        }
        if (request.getAddress() != null) {
            pharmacy.setAddress(request.getAddress());
        }
        if (request.getPhone() != null) {
            pharmacy.setPhone(request.getPhone());
        }

        if (request.getLocation() != null) {
            pharmacy.setLocation(request.getLocation());
        }
 
        if (request.getServices() != null) {
            // TODO: Handle services field - might need proper conversion
            // pharmacy.setServices(request.getServices());
        }
        if (request.getVaccines() != null) {
            // TODO: Handle vaccines field - UUIDs to Vaccine entities conversion needed
            // pharmacy.setVaccines(request.getVaccines());
        }

        return pharmacyMapper.toDTO(pharmacyRepository.save(pharmacy));
    }
    
    
    /**
     * Create empty pharmacy for logged user
     */
    public PharmacyDTO createPharmacyForUser(String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        // Check if user already has a pharmacy
        if (pharmacyRepository.existsByOwner(owner)) {
            throw new IllegalArgumentException("User already has a pharmacy");
        }
        
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setOwner(owner);
        pharmacy.setVerificationStatus(VerificationStatus.UNVERIFIED);
        pharmacy.setMissingDocuments(new ArrayList<>());
        
        Pharmacy savedPharmacy = pharmacyRepository.save(pharmacy);
        return pharmacyMapper.toDTO(savedPharmacy);
    }
    
    /**
     * Update pharmacy profile for logged user
     */
    public PharmacyDTO updatePharmacyProfile(String userEmail, PharmacyRegistrationRequest request) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Pharmacy pharmacy = pharmacyRepository.findByOwner(owner)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found for user"));
        
        // Validate unique license number if provided
        if (request.getLicenseNumber() != null && !request.getLicenseNumber().isEmpty()) {
            boolean exists = pharmacyRepository.existsByLicenseNumber(request.getLicenseNumber());
            if (exists && !request.getLicenseNumber().equals(pharmacy.getLicenseNumber())) {
                throw new IllegalArgumentException("Pharmacy with license number already exists");
            }
        }
        
        // Update basic info
        if (request.getName() != null) pharmacy.setName(request.getName());
        if (request.getAddress() != null) pharmacy.setAddress(request.getAddress());
        if (request.getPhone() != null) pharmacy.setPhone(request.getPhone());
        
        // Update business information
        if (request.getLicenseNumber() != null) pharmacy.setLicenseNumber(request.getLicenseNumber());
        if (request.getRegistrationDate() != null) pharmacy.setRegistrationDate(request.getRegistrationDate());
        if (request.getTinNumber() != null) pharmacy.setTinNumber(request.getTinNumber());
        if (request.getPharmacyType() != null) pharmacy.setPharmacyType(request.getPharmacyType());
        if (request.getOwnershipType() != null) pharmacy.setOwnershipType(request.getOwnershipType());
        
        // Update location information
        if (request.getProvince() != null) pharmacy.setProvince(request.getProvince());
        if (request.getDistrict() != null) pharmacy.setDistrict(request.getDistrict());
        if (request.getSector() != null) pharmacy.setSector(request.getSector());
        if (request.getCell() != null) pharmacy.setCell(request.getCell());
        if (request.getVillage() != null) pharmacy.setVillage(request.getVillage());
        
        // Update GPS coordinates
        if (request.getLatitude() != null && request.getLongitude() != null) {
            Coordinates coordinates = new Coordinates();
            coordinates.setLatitude(request.getLatitude());
            coordinates.setLongitude(request.getLongitude());
            pharmacy.setLocation(coordinates);
        }
        
        // Update premises information
        if (request.getOwnershipStatus() != null) pharmacy.setOwnershipStatus(request.getOwnershipStatus());
        if (request.getPremisesSize() != null) pharmacy.setPremisesSize(request.getPremisesSize());
        if (request.getStorageFacilities() != null) pharmacy.setStorageFacilities(request.getStorageFacilities());
        
        // Update pharmacist information if provided
        if (request.getPharmacistName() != null) pharmacy.setPharmacistName(request.getPharmacistName());
        if (request.getPharmacistLicenseNumber() != null) pharmacy.setPharmacistLicenseNumber(request.getPharmacistLicenseNumber());
        if (request.getPharmacistPhone() != null) pharmacy.setPharmacistPhone(request.getPharmacistPhone());
        if (request.getPharmacistEmail() != null) pharmacy.setPharmacistEmail(request.getPharmacistEmail());
        
        // Check if profile is complete for verification
        if (isPharmacyProfileComplete(pharmacy)) {
            pharmacy.setVerificationStatus(VerificationStatus.PENDING);
        }
        
        Pharmacy savedPharmacy = pharmacyRepository.save(pharmacy);
        return pharmacyMapper.toDTO(savedPharmacy);
    }
    
    /**
     * Check if pharmacy profile is complete
     */
    private boolean isPharmacyProfileComplete(Pharmacy pharmacy) {
        return pharmacy.getName() != null && !pharmacy.getName().isEmpty() &&
               pharmacy.getAddress() != null && !pharmacy.getAddress().isEmpty() &&
               pharmacy.getPhone() != null && !pharmacy.getPhone().isEmpty() &&
               pharmacy.getLicenseNumber() != null && !pharmacy.getLicenseNumber().isEmpty() &&
               pharmacy.getProvince() != null && !pharmacy.getProvince().isEmpty() &&
               pharmacy.getDistrict() != null && !pharmacy.getDistrict().isEmpty();
    }
    
    /**
     * Update document path and remove from missing documents list
     */
    public void updateDocumentPath(UUID pharmacyId, String documentType, String filePath) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));
        
        // Update appropriate document path
        switch (documentType) {
            case "BUSINESS_LICENSE":
                pharmacy.setBusinessLicensePath(filePath);
                break;
            case "PHARMACIST_LICENSE":
                pharmacy.setPharmacistLicensePath(filePath);
                break;
            case "PREMISES_INSPECTION_REPORT":
                pharmacy.setPremisesInspectionPath(filePath);
                break;
            case "TAX_CLEARANCE_CERTIFICATE":
                pharmacy.setTaxClearancePath(filePath);
                break;
            case "INSURANCE_CERTIFICATE":
                pharmacy.setInsuranceCertificatePath(filePath);
                break;
            case "COMPLIANCE_CERTIFICATE":
                pharmacy.setComplianceCertificatePath(filePath);
                break;
            case "PHARMACIST_PHOTO_ID":
                // Handle pharmacist photo if needed
                break;
            default:
                throw new IllegalArgumentException("Invalid document type: " + documentType);
        }
        
        // Remove from missing documents list
        pharmacy.getMissingDocuments().remove(documentType);
        
        // If all documents uploaded, set status to PENDING
        if (pharmacy.getMissingDocuments().isEmpty() && 
            pharmacy.getVerificationStatus() == VerificationStatus.UNVERIFIED) {
            pharmacy.setVerificationStatus(VerificationStatus.PENDING);
        }
        
        pharmacyRepository.save(pharmacy);
    }
    
    /**
     * Get verification status for a pharmacy
     */
    public VerificationStatus getVerificationStatus(UUID pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));
        return pharmacy.getVerificationStatus();
    }
    
    /**
     * Get missing documents list
     */
    public List<String> getMissingDocuments(UUID pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));
        return pharmacy.getMissingDocuments();
    }
    
    /**
     * Admin verification workflow
     */
    public PharmacyDTO verifyPharmacy(PharmacyVerificationRequest request) {
        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));
        
        pharmacy.setVerificationStatus(request.getVerificationStatus());
        pharmacy.setAdminComments(request.getAdminComments());
        pharmacy.setLastReviewedBy(request.getReviewerName());
        pharmacy.setLastReviewedAt(LocalDateTime.now());
        
        if (request.getMissingDocuments() != null) {
            pharmacy.setMissingDocuments(request.getMissingDocuments());
        }
        
        Pharmacy savedPharmacy = pharmacyRepository.save(pharmacy);
        return pharmacyMapper.toDTO(savedPharmacy);
    }
    
    /**
     * Get pharmacies pending verification
     */
    public List<PharmacyDTO> getPendingVerifications() {
        List<Pharmacy> pendingPharmacies = pharmacyRepository.findByVerificationStatus(VerificationStatus.PENDING);
        return pendingPharmacies.stream()
                .map(pharmacyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deletePharmacy(UUID id) {
        pharmacyRepository.deleteById(id);
    }
}
