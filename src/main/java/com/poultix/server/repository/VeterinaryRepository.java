package com.poultix.server.repository;

import com.poultix.server.entities.Veterinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeterinaryRepository extends JpaRepository<Veterinary, UUID> {
    Optional<Veterinary> findByUserId(UUID userId);
    
    boolean existsByUserId(UUID userId);
    
    List<Veterinary> findByIsActive(Boolean isActive);
    
    List<Veterinary> findByRatingGreaterThanEqual(Double rating);
    
}
