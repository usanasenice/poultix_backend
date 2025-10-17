package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.dto.VaccineDTO;
import com.poultix.server.dto.request.VaccineCreateRequest;
import com.poultix.server.dto.request.VaccineUpdateRequest;
import com.poultix.server.service.VaccineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vaccines")
@RequiredArgsConstructor
public class VaccineController {
    
    private final VaccineService vaccineService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<VaccineDTO>> createVaccine(@Valid @RequestBody VaccineCreateRequest request) {
        VaccineDTO response = vaccineService.createVaccine(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Vaccine created successfully", response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VaccineDTO>> getVaccineById(@PathVariable UUID id) {
        VaccineDTO response = vaccineService.getVaccineById(id);
        return ResponseEntity.ok(ApiResponse.success("Vaccine retrieved successfully", response));
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<VaccineDTO>> getVaccineByName(@PathVariable String name) {
        VaccineDTO response = vaccineService.getVaccineByName(name);
        return ResponseEntity.ok(ApiResponse.success("Vaccine retrieved successfully", response));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<VaccineDTO>>> getAllVaccines() {
        List<VaccineDTO> vaccines = vaccineService.getAllVaccines();
        return ResponseEntity.ok(ApiResponse.success("Vaccines retrieved successfully", vaccines));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<VaccineDTO>>> getVaccinesByType(@PathVariable String type) {
        List<VaccineDTO> vaccines = vaccineService.getVaccinesByType(type);
        return ResponseEntity.ok(ApiResponse.success("Vaccines retrieved by type successfully", vaccines));
    }
    
    @GetMapping("/target-disease/{targetDisease}")
    public ResponseEntity<ApiResponse<List<VaccineDTO>>> getVaccinesByTargetDisease(@PathVariable String targetDisease) {
        List<VaccineDTO> vaccines = vaccineService.getVaccinesByTargetDisease(targetDisease);
        return ResponseEntity.ok(ApiResponse.success("Vaccines retrieved by target disease successfully", vaccines));
    }
    
    @GetMapping("/prescription-required/{prescriptionRequired}")
    public ResponseEntity<ApiResponse<List<VaccineDTO>>> getVaccinesByPrescriptionRequired(@PathVariable boolean prescriptionRequired) {
        List<VaccineDTO> vaccines = vaccineService.getVaccinesByPrescriptionRequired(prescriptionRequired);
        return ResponseEntity.ok(ApiResponse.success("Vaccines retrieved by prescription requirement successfully", vaccines));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<VaccineDTO>>> searchVaccines(@RequestParam String keyword) {
        List<VaccineDTO> vaccines = vaccineService.searchVaccines(keyword);
        return ResponseEntity.ok(ApiResponse.success("Vaccines search completed successfully", vaccines));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VaccineDTO>> updateVaccine(@PathVariable UUID id, @Valid @RequestBody VaccineUpdateRequest request) {
        VaccineDTO response = vaccineService.updateVaccine(id, request);
        return ResponseEntity.ok(ApiResponse.success("Vaccine updated successfully", response));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVaccine(@PathVariable UUID id) {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.ok(ApiResponse.success("Vaccine deleted successfully", null));
    }
    
    @GetMapping("/exists/name/{name}")
    public ResponseEntity<ApiResponse<Boolean>> checkVaccineExists(@PathVariable String name) {
        boolean exists = vaccineService.existsByName(name);
        return ResponseEntity.ok(ApiResponse.success("Vaccine existence check completed", exists));
    }
}
