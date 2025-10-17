package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.dto.request.VeterinaryCreateRequest;
import com.poultix.server.dto.request.VeterinaryUpdateRequest;
import com.poultix.server.dto.VeterinaryDTO;
import com.poultix.server.service.VeterinaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/veterinaries")
@RequiredArgsConstructor
public class VeterinaryController {
    
    private final VeterinaryService veterinaryService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<VeterinaryDTO>> createVeterinary(@Valid @RequestBody VeterinaryCreateRequest request, @RequestParam UUID userId) {
        VeterinaryDTO response = veterinaryService.createVeterinary(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Veterinary profile created successfully", response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VeterinaryDTO>> getVeterinaryById(@PathVariable UUID id) {
        VeterinaryDTO response = veterinaryService.getVeterinaryById(id);
        return ResponseEntity.ok(ApiResponse.success("Veterinary retrieved successfully", response));
    }
  
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<VeterinaryDTO>> getVeterinaryByUserId(@PathVariable UUID userId) {
        VeterinaryDTO response = veterinaryService.getVeterinaryByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("Veterinary retrieved successfully", response));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<VeterinaryDTO>>> getAllVeterinaries() {
        List<VeterinaryDTO> veterinaries = veterinaryService.getAllVeterinaries();
        return ResponseEntity.ok(ApiResponse.success("Veterinaries retrieved successfully", veterinaries));
    }
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<VeterinaryDTO>>> getActiveVeterinaries() {
        List<VeterinaryDTO> veterinaries = veterinaryService.getActiveVeterinaries();
        return ResponseEntity.ok(ApiResponse.success("Active veterinaries retrieved successfully", veterinaries));
    }
    
    @GetMapping("/top-rated")
    public ResponseEntity<ApiResponse<List<VeterinaryDTO>>> getTopRatedVeterinaries(@RequestParam(defaultValue = "4.0") Double minRating) {
        List<VeterinaryDTO> veterinaries = veterinaryService.getTopRatedVeterinaries(minRating);
        return ResponseEntity.ok(ApiResponse.success("Top-rated veterinaries retrieved successfully", veterinaries));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VeterinaryDTO>> updateVeterinary(@PathVariable UUID id, @Valid @RequestBody VeterinaryUpdateRequest request) {
        VeterinaryDTO response = veterinaryService.updateVeterinary(id, request);
        return ResponseEntity.ok(ApiResponse.success("Veterinary profile updated successfully", response));
    }
    
    @PatchMapping("/{id}/rating")
    public ResponseEntity<ApiResponse<Void>> updateRating(@PathVariable UUID id, @RequestParam double rating) {
        veterinaryService.updateRating(id, rating);
        return ResponseEntity.ok(ApiResponse.success("Veterinary rating updated successfully", null));
    }
    
    @PatchMapping("/{id}/increment-visits")
    public ResponseEntity<ApiResponse<Void>> incrementVisits(@PathVariable UUID id) {
        veterinaryService.incrementTotalVisits(id);
        return ResponseEntity.ok(ApiResponse.success("Veterinary visits incremented successfully", null));
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateVeterinary(@PathVariable UUID id) {
        veterinaryService.deactivateVeterinary(id);
        return ResponseEntity.ok(ApiResponse.success("Veterinary deactivated successfully", null));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVeterinary(@PathVariable UUID id) {
        veterinaryService.deleteVeterinary(id);
        return ResponseEntity.ok(ApiResponse.success("Veterinary deleted successfully", null));
    }
}
