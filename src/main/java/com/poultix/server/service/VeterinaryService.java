package com.poultix.server.service;

import com.poultix.server.dto.request.VeterinaryCreateRequest;
import com.poultix.server.dto.request.VeterinaryUpdateRequest;
import com.poultix.server.dto.VeterinaryDTO;
import com.poultix.server.entities.User;
import com.poultix.server.entities.Veterinary;
import com.poultix.server.mappers.VeterinaryMapper;
import com.poultix.server.repository.UserRepository;
import com.poultix.server.repository.VeterinaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VeterinaryService {

    private final VeterinaryRepository veterinaryRepository;
    private final VeterinaryMapper veterinaryMapper;
    private final UserRepository userRepository;

    /**
     * Create veterinary profile
     */
    public VeterinaryDTO createVeterinary(VeterinaryCreateRequest request, UUID userId) {
        if (veterinaryRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("Veterinary profile already exists for user");
        }

        // Create new veterinary profile
        Veterinary veterinary = new Veterinary();
        veterinary.setServiceRadius(request.getServiceRadius());

        // Fetch and set user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        veterinary.setUser(user);

        Veterinary savedVeterinary = veterinaryRepository.save(veterinary);
        return veterinaryMapper.toDTO(savedVeterinary);
    }

    /**
     * Get veterinary by ID
     */
    @Transactional(readOnly = true)
    public VeterinaryDTO getVeterinaryById(UUID id) {
        Veterinary veterinary = veterinaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinary not found with id: " + id));
        return veterinaryMapper.toDTO(veterinary);
    }

    /**
     * Get veterinary by user ID
     */
    @Transactional(readOnly = true)
    public VeterinaryDTO getVeterinaryByUserId(UUID userId) {
        Veterinary veterinary = veterinaryRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Veterinary not found for user: " + userId));
        return veterinaryMapper.toDTO(veterinary);
    }

    /**
     * Get all veterinaries
     */
    @Transactional(readOnly = true)
    public List<VeterinaryDTO> getAllVeterinaries() {
        return veterinaryRepository.findAll().stream()
                .map(veterinaryMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get active veterinaries
     */
    @Transactional(readOnly = true)
    public List<VeterinaryDTO> getActiveVeterinaries() {
        return veterinaryRepository.findByIsActive(true).stream()
                .map(veterinaryMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get top rated veterinaries
     */
    @Transactional(readOnly = true)
    public List<VeterinaryDTO> getTopRatedVeterinaries(Double minRating) {
        return veterinaryRepository.findByRatingGreaterThanEqual(minRating).stream()
                .map(veterinaryMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update veterinary profile
     */
    public VeterinaryDTO updateVeterinary(UUID id, VeterinaryUpdateRequest request) {
        Veterinary veterinary = veterinaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinary not found with id: " + id));

        if (request.getServiceRadius() != null) {
            veterinary.setServiceRadius(request.getServiceRadius());
        }
        Veterinary updatedVeterinary = veterinaryRepository.save(veterinary);
        return veterinaryMapper.toDTO(updatedVeterinary);
    }

    /**
     * Update rating
     */
    public void updateRating(UUID id, double newRating) {
        Veterinary veterinary = veterinaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinary not found with id: " + id));

        // Calculate average rating (simple implementation)
        int totalVisits = veterinary.getTotalVisits();
        double currentRating = veterinary.getRating();
        double updatedRating = ((currentRating * totalVisits) + newRating) / (totalVisits + 1);

        veterinary.setRating(Math.round(updatedRating * 10.0) / 10.0); // Round to 1 decimal
        veterinaryRepository.save(veterinary);
    }

    /**
     * Increment total visits
     */
    public void incrementTotalVisits(UUID id) {
        Veterinary veterinary = veterinaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinary not found with id: " + id));

        veterinary.setTotalVisits(veterinary.getTotalVisits() + 1);
        veterinaryRepository.save(veterinary);
    }

    /**
     * Add farm to managed farms
     */
    public void addManagedFarm(UUID veterinaryId, UUID farmId) {
        Veterinary veterinary = veterinaryRepository.findById(veterinaryId)
                .orElseThrow(() -> new IllegalArgumentException("Veterinary not found with id: " + veterinaryId));

        if (!veterinary.getFarmManaged().contains(farmId)) {
            veterinary.getFarmManaged().add(farmId);
            veterinaryRepository.save(veterinary);
        }
    }

    /**
     * Remove farm from managed farms
     */
    public void removeManagedFarm(UUID veterinaryId, UUID farmId) {
        Veterinary veterinary = veterinaryRepository.findById(veterinaryId)
                .orElseThrow(() -> new IllegalArgumentException("Veterinary not found with id: " + veterinaryId));

        veterinary.getFarmManaged().remove(farmId);
        veterinaryRepository.save(veterinary);
    }

    /**
     * Deactivate veterinary
     */
    public void deactivateVeterinary(UUID id) {
        Veterinary veterinary = veterinaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinary not found with id: " + id));
        veterinary.setIsActive(false);
        veterinaryRepository.save(veterinary);
    }

    /**
     * Delete veterinary
     */
    public void deleteVeterinary(UUID id) {
        if (!veterinaryRepository.existsById(id)) {
            throw new IllegalArgumentException("Veterinary not found with id: " + id);
        }
        veterinaryRepository.deleteById(id);
    }
}
