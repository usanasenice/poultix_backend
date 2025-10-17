package com.poultix.server.service;

import com.poultix.server.dto.VaccineDTO;
import com.poultix.server.dto.request.VaccineCreateRequest;
import com.poultix.server.dto.request.VaccineUpdateRequest;
import com.poultix.server.entities.Vaccine;
import com.poultix.server.mappers.VaccineMapper;
import com.poultix.server.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VaccineService {
    
    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;
    
    /**
     * Create a new vaccine
     */
    public VaccineDTO createVaccine(VaccineCreateRequest request) {
        if (vaccineRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Vaccine with name '" + request.getName() + "' already exists");
        }
        
        Vaccine vaccine = new Vaccine();
        vaccine.setName(request.getName());
        vaccine.setType(request.getType());
        vaccine.setTargetDisease(request.getTargetDisease());
        vaccine.setDosage(request.getDosage());
        vaccine.setAdministration(request.getAdministration());
        vaccine.setStorage(request.getStorage());
        vaccine.setPrescriptionRequired(request.getPrescriptionRequired());
        
        Vaccine savedVaccine = vaccineRepository.save(vaccine);
        return vaccineMapper.toDTO(savedVaccine);
    }
    
    /**
     * Get vaccine by ID
     */
    @Transactional(readOnly = true)
    public VaccineDTO getVaccineById(UUID id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaccine not found with id: " + id));
        return vaccineMapper.toDTO(vaccine);
    }
    
    /**
     * Get vaccine by name
     */
    @Transactional(readOnly = true)
    public VaccineDTO getVaccineByName(String name) {
        Vaccine vaccine = vaccineRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Vaccine not found with name: " + name));
        return vaccineMapper.toDTO(vaccine);
    }
    
    /**
     * Get all vaccines
     */
    @Transactional(readOnly = true)
    public List<VaccineDTO> getAllVaccines() {
        return vaccineRepository.findAll().stream()
                .map(vaccineMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get vaccines by type
     */
    @Transactional(readOnly = true)
    public List<VaccineDTO> getVaccinesByType(String type) {
        return vaccineRepository.findByType(type).stream()
                .map(vaccineMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get vaccines by target disease
     */
    @Transactional(readOnly = true)
    public List<VaccineDTO> getVaccinesByTargetDisease(String targetDisease) {
        return vaccineRepository.findByTargetDisease(targetDisease).stream()
                .map(vaccineMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get vaccines by prescription requirement
     */
    @Transactional(readOnly = true)
    public List<VaccineDTO> getVaccinesByPrescriptionRequired(boolean prescriptionRequired) {
        return vaccineRepository.findByPrescriptionRequired(prescriptionRequired).stream()
                .map(vaccineMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Search vaccines by keyword
     */
    @Transactional(readOnly = true)
    public List<VaccineDTO> searchVaccines(String keyword) {
        return vaccineRepository.findByKeyword(keyword).stream()
                .map(vaccineMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update vaccine
     */
    public VaccineDTO updateVaccine(UUID id, VaccineUpdateRequest request) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaccine not found with id: " + id));
        
        if (request.getName() != null && !request.getName().equals(vaccine.getName())) {
            if (vaccineRepository.existsByName(request.getName())) {
                throw new IllegalArgumentException("Vaccine with name '" + request.getName() + "' already exists");
            }
            vaccine.setName(request.getName());
        }
        
        if (request.getType() != null) {
            vaccine.setType(request.getType());
        }
        
        if (request.getTargetDisease() != null) {
            vaccine.setTargetDisease(request.getTargetDisease());
        }
        
        if (request.getDosage() != null) {
            vaccine.setDosage(request.getDosage());
        }
        
        if (request.getAdministration() != null) {
            vaccine.setAdministration(request.getAdministration());
        }
        
        if (request.getStorage() != null) {
            vaccine.setStorage(request.getStorage());
        }
        
        if (request.getPrescriptionRequired() != null) {
            vaccine.setPrescriptionRequired(request.getPrescriptionRequired());
        }
        
        Vaccine updatedVaccine = vaccineRepository.save(vaccine);
        return vaccineMapper.toDTO(updatedVaccine);
    }
    
    /**
     * Delete vaccine by ID
     */
    public void deleteVaccine(UUID id) {
        if (!vaccineRepository.existsById(id)) {
            throw new IllegalArgumentException("Vaccine not found with id: " + id);
        }
        vaccineRepository.deleteById(id);
    }
    
    /**
     * Check if vaccine exists by name
     */
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return vaccineRepository.existsByName(name);
    }
}
