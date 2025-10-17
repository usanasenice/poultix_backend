package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.dto.ScheduleDTO;
import com.poultix.server.dto.request.ScheduleCreateRequest;
import com.poultix.server.dto.request.ScheduleUpdateRequest;
import com.poultix.server.entities.enums.ScheduleStatus;
import com.poultix.server.service.ScheduleService;
import com.poultix.server.service.UserService;
import com.poultix.server.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    private final UserService userService;

    public UUID getUserId(Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return user.getId();
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleDTO>> createSchedule(@Valid @RequestBody ScheduleCreateRequest request, Principal principal) {
        UUID createdBy = getUserId(principal);
        ScheduleDTO response = scheduleService.createSchedule(request, createdBy);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Schedule created successfully", response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleDTO>> getScheduleById(@PathVariable UUID id) {
        ScheduleDTO response = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(ApiResponse.success("Schedule retrieved successfully", response));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleDTO>>> getAllSchedules() {
        List<ScheduleDTO> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(ApiResponse.success("Schedules retrieved successfully", schedules));
    }
    
    @GetMapping("/farmer")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByFarmer(Principal principal) {
        UUID farmerId = getUserId(principal);
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByFarmerId(farmerId);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/veterinary")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByVeterinary(Principal principal) {
        UUID veterinaryId = getUserId(principal);
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByVeterinaryId(veterinaryId);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByStatus(@PathVariable ScheduleStatus status) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByStatus(status);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByDate(date);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByDateRange(startDate, endDate);
        return ResponseEntity.ok(schedules);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable UUID id, @Valid @RequestBody ScheduleUpdateRequest request) {
        ScheduleDTO response = scheduleService.updateSchedule(id, request);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<ScheduleDTO> updateStatus(@PathVariable UUID id, @RequestParam ScheduleStatus status) {
        ScheduleDTO response = scheduleService.updateScheduleStatus(id, status);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}/complete")
    public ResponseEntity<ScheduleDTO> completeSchedule(@PathVariable UUID id) {
        ScheduleDTO response = scheduleService.completeSchedule(id);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ScheduleDTO> cancelSchedule(@PathVariable UUID id) {
        ScheduleDTO response = scheduleService.cancelSchedule(id);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable UUID id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
