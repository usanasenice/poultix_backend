package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.dto.request.FarmCreateRequest;
import com.poultix.server.dto.request.FarmUpdateRequest;
import com.poultix.server.dto.FarmDTO;
import com.poultix.server.dto.UserDTO;
import com.poultix.server.entities.enums.FarmStatus;
import com.poultix.server.service.FarmService;
import com.poultix.server.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farms")
@RequiredArgsConstructor
public class FarmController {

    private final UserService userService;
    private final FarmService farmService;

    public UUID getOwnerId(Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return user.getId();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FarmDTO>> createFarm(@Valid @RequestBody FarmCreateRequest request, Principal principal) {
        UUID ownerId = getOwnerId(principal);
        FarmDTO response = farmService.createFarm(request, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Farm created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FarmDTO>> getFarmById(@PathVariable UUID id) {
        FarmDTO response = farmService.getFarmById(id);
        return ResponseEntity.ok(ApiResponse.success("Farm retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FarmDTO>>> getAllFarms() {
        List<FarmDTO> farms = farmService.getAllFarms();
        return ResponseEntity.ok(ApiResponse.success("Farms retrieved successfully", farms));
    }

    @GetMapping("/owner")
    public ResponseEntity<ApiResponse<List<FarmDTO>>> getFarmsByOwner(Principal principal) {
        UUID ownerId = getOwnerId(principal);
        List<FarmDTO> farms = farmService.getFarmsByOwnerId(ownerId);
        return ResponseEntity.ok(ApiResponse.success("Owner farms retrieved successfully", farms));
    }

    @GetMapping("/veterinary")
    public ResponseEntity<ApiResponse<List<FarmDTO>>> getFarmsByVeterinary(Principal principal) {
        UUID veterinaryId = getOwnerId(principal);
        List<FarmDTO> farms = farmService.getFarmsByVeterinaryId(veterinaryId);
        return ResponseEntity.ok(ApiResponse.success("Veterinary farms retrieved successfully", farms));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<FarmDTO>>> getFarmsByHealthStatus(@PathVariable FarmStatus status) {
        List<FarmDTO> farms = farmService.getFarmsByHealthStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Farms by status retrieved successfully", farms));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<FarmDTO>>> getActiveFarms() {
        List<FarmDTO> farms = farmService.getActiveFarms();
        return ResponseEntity.ok(ApiResponse.success("Active farms retrieved successfully", farms));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FarmDTO>> updateFarm(@PathVariable UUID id,
            @Valid @RequestBody FarmUpdateRequest request) {
        FarmDTO response = farmService.updateFarm(id, request);
        return ResponseEntity.ok(ApiResponse.success("Farm updated successfully", response));
    }

    @PatchMapping("/{id}/assign-veterinary")
    public ResponseEntity<ApiResponse<FarmDTO>> assignVeterinary(@PathVariable UUID id,Principal principal) {
        UUID veterinaryId = getOwnerId(principal);
        FarmDTO response = farmService.assignVeterinary(id, veterinaryId);
        return ResponseEntity.ok(ApiResponse.success("Veterinary assigned successfully", response));
    }

    @PatchMapping("/{id}/health-status")
    public ResponseEntity<ApiResponse<FarmDTO>> updateHealthStatus(@PathVariable UUID id,
            @RequestParam FarmStatus status) {
        FarmDTO response = farmService.updateHealthStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Farm health status updated successfully", response));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateFarm(@PathVariable UUID id) {
        farmService.deactivateFarm(id);
        return ResponseEntity.ok(ApiResponse.success("Farm deactivated successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFarm(@PathVariable UUID id) {
        farmService.deleteFarm(id);
        return ResponseEntity.ok(ApiResponse.success("Farm deleted successfully", null));
    }
}
