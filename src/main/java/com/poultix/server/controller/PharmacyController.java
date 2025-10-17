package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.dto.request.PharmacyCreateRequest;
import com.poultix.server.dto.request.PharmacyUpdateRequest;
import com.poultix.server.dto.request.PharmacyRegistrationRequest;
import com.poultix.server.dto.request.PharmacyVerificationRequest;
import com.poultix.server.dto.PharmacyDTO;
import com.poultix.server.dto.DocumentUploadDTO;
import com.poultix.server.service.PharmacyService;
import com.poultix.server.service.FileUploadService;
import com.poultix.server.constants.LocationConstants;
import com.poultix.server.entities.enums.VerificationStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {
    
    private final PharmacyService pharmacyService;
    private final FileUploadService fileUploadService;
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PharmacyDTO>> createPharmacyForUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName(); // Assuming email is used as username
        
        PharmacyDTO response = pharmacyService.createPharmacyForUser(userEmail);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Empty pharmacy created. Please update profile to complete registration.", response));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<PharmacyDTO>> createPharmacy(@Valid @RequestBody PharmacyCreateRequest request) {
        PharmacyDTO response = pharmacyService.createPharmacy(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pharmacy created successfully", response));
    }
    
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<PharmacyDTO>> updatePharmacyProfile(
            @Valid @RequestBody PharmacyRegistrationRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        PharmacyDTO response = pharmacyService.updatePharmacyProfile(userEmail, request);
        return ResponseEntity.ok(ApiResponse.success("Pharmacy profile updated successfully.", response));
    }
    
    
    // Document upload endpoint
    @PostMapping("/{pharmacyId}/documents/{documentType}")
    public ResponseEntity<ApiResponse<DocumentUploadDTO>> uploadDocument(
            @PathVariable UUID pharmacyId,
            @PathVariable String documentType,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "uploadedBy", defaultValue = "system") String uploadedBy) throws IOException {
        
        DocumentUploadDTO response = fileUploadService.uploadPharmacyDocument(file, documentType.toUpperCase(), pharmacyId, uploadedBy);
        pharmacyService.updateDocumentPath(pharmacyId, documentType.toUpperCase(), response.getFilePath());
        
        return ResponseEntity.ok(ApiResponse.success("Document uploaded successfully", response));
    }
    
    // Get verification status
    @GetMapping("/{pharmacyId}/verification-status")
    public ResponseEntity<ApiResponse<VerificationStatus>> getVerificationStatus(@PathVariable UUID pharmacyId) {
        VerificationStatus status = pharmacyService.getVerificationStatus(pharmacyId);
        return ResponseEntity.ok(ApiResponse.success("Verification status retrieved", status));
    }
    
    // Get missing documents
    @GetMapping("/{pharmacyId}/missing-documents")
    public ResponseEntity<ApiResponse<List<String>>> getMissingDocuments(@PathVariable UUID pharmacyId) {
        List<String> missingDocs = pharmacyService.getMissingDocuments(pharmacyId);
        return ResponseEntity.ok(ApiResponse.success("Missing documents retrieved", missingDocs));
    }
    
    // Admin verification endpoint
    @PostMapping("/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PharmacyDTO>> verifyPharmacy(@Valid @RequestBody PharmacyVerificationRequest request) {
        PharmacyDTO response = pharmacyService.verifyPharmacy(request);
        return ResponseEntity.ok(ApiResponse.success("Pharmacy verification updated", response));
    }
    
    // Get pending verifications (Admin only)
    @GetMapping("/pending-verifications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PharmacyDTO>>> getPendingVerifications() {
        List<PharmacyDTO> pendingPharmacies = pharmacyService.getPendingVerifications();
        return ResponseEntity.ok(ApiResponse.success("Pending verifications retrieved", pendingPharmacies));
    }
    
    // Location data endpoints
    @GetMapping("/provinces")
    public ResponseEntity<ApiResponse<List<String>>> getProvinces() {
        return ResponseEntity.ok(ApiResponse.success("Provinces retrieved", LocationConstants.PROVINCES));
    }
    
    @GetMapping("/districts/{province}")
    public ResponseEntity<ApiResponse<List<String>>> getDistrictsByProvince(@PathVariable String province) {
        List<String> districts = LocationConstants.DISTRICTS.get(province.toUpperCase());
        if (districts == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid province: " + province));
        }
        return ResponseEntity.ok(ApiResponse.success("Districts retrieved", districts));
    }
    
    @GetMapping("/required-documents")
    public ResponseEntity<ApiResponse<List<String>>> getRequiredDocuments() {
        return ResponseEntity.ok(ApiResponse.success("Required documents retrieved", LocationConstants.REQUIRED_DOCUMENTS));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PharmacyDTO>> getPharmacyById(@PathVariable UUID id) {
        PharmacyDTO response = pharmacyService.getPharmacyById(id);
        return ResponseEntity.ok(ApiResponse.success("Pharmacy retrieved successfully", response));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<PharmacyDTO>>> getAllPharmacies() {
        List<PharmacyDTO> pharmacies = pharmacyService.getAllPharmacies();
        return ResponseEntity.ok(ApiResponse.success("Pharmacies retrieved successfully", pharmacies));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PharmacyDTO>> updatePharmacy(@PathVariable UUID id, @Valid @RequestBody PharmacyUpdateRequest request) {
        PharmacyDTO response = pharmacyService.updatePharmacy(id, request);
        return ResponseEntity.ok(ApiResponse.success("Pharmacy updated successfully", response));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePharmacy(@PathVariable UUID id) {
        pharmacyService.deletePharmacy(id);
        return ResponseEntity.ok(ApiResponse.success("Pharmacy deleted successfully", null));
    }
}
