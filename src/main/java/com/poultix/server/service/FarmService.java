package com.poultix.server.service;

import com.poultix.server.dto.request.FarmCreateRequest;
import com.poultix.server.dto.request.FarmUpdateRequest;
import com.poultix.server.dto.FarmDTO;
import com.poultix.server.entities.Farm;
import com.poultix.server.entities.User;
import com.poultix.server.entities.enums.FarmStatus;
import com.poultix.server.mappers.FarmMapper;
import com.poultix.server.repository.FarmRepository;
import com.poultix.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FarmService {
    
    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final UserRepository userRepository;
    
    /**
     * Create a new farm
     */
    public FarmDTO createFarm(FarmCreateRequest request, UUID ownerId) {
        // Create new farm
        Farm farm = new Farm();
        farm.setName(request.getName());
        farm.setLocation(request.getLocation());
        farm.setLivestock(request.getLivestock());
        farm.setFacilities(request.getFacilities());
        
        // Fetch and set owner
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with id: " + ownerId));
        farm.setOwner(owner);
        
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(savedFarm);
    }
    
    /**
     */
    @Transactional(readOnly = true)
    public FarmDTO getFarmById(UUID id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + id));
        return farmMapper.toDTO(farm);
    }
    
    /**
     * Get all farms
     */
    @Transactional(readOnly = true)
    public List<FarmDTO> getAllFarms() {
        return farmRepository.findAll().stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get farms by owner ID
     */
    @Transactional(readOnly = true)
    public List<FarmDTO> getFarmsByOwnerId(UUID ownerId) {
        return farmRepository.findByOwner_Id(ownerId).stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get farms by veterinary ID
     */
    @Transactional(readOnly = true)
    public List<FarmDTO> getFarmsByVeterinaryId(UUID veterinaryId) {
        return farmRepository.findByAssignedVeterinary_Id(veterinaryId).stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get farms by health status
     */
    @Transactional(readOnly = true)
    public List<FarmDTO> getFarmsByHealthStatus(FarmStatus healthStatus) {
        return farmRepository.findByHealthStatus(healthStatus).stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get active farms
     */
    @Transactional(readOnly = true)
    public List<FarmDTO> getActiveFarms() {
        return farmRepository.findByIsActive(true).stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update farm
     */
    public FarmDTO updateFarm(UUID id, FarmUpdateRequest request) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + id));
        
        // Update farm fields manually
        if (request.getName() != null) {
            farm.setName(request.getName());
        }
        if (request.getLocation() != null) {
            farm.setLocation(request.getLocation());
        }
        if (request.getLivestock() != null) {
            farm.setLivestock(request.getLivestock());
        }
        if (request.getFacilities() != null) {
            farm.setFacilities(request.getFacilities());
        }

        if (request.getIsActive() != null) {
            farm.setIsActive(request.getIsActive());
        }
        
        Farm updatedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(updatedFarm);
    }
    
    /**
     * Assign veterinary to farm
     */
    public FarmDTO assignVeterinary(UUID farmId, UUID veterinaryId) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + farmId));
        User veterinary=userRepository.findById(veterinaryId).orElseThrow(
            
        );
        farm.setAssignedVeterinary(veterinary); 
        Farm updatedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(updatedFarm);
    }
    
    /**
     * Update health status
     */
    public FarmDTO updateHealthStatus(UUID farmId, FarmStatus healthStatus) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + farmId));
        
        farm.setHealthStatus(healthStatus);
        farm.setLastInspection(LocalDate.now());
        Farm updatedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(updatedFarm);
    }
    
    /**
     * Deactivate farm
     */
    public void deactivateFarm(UUID id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + id));
        farm.setIsActive(false);
        farmRepository.save(farm);
    }
    
    /**
     * Delete farm
     */
    public void deleteFarm(UUID id) {
        if (!farmRepository.existsById(id)) {
            throw new IllegalArgumentException("Farm not found with id: " + id);
        }
        farmRepository.deleteById(id);
    }
}
