package com.poultix.server.repository;

import com.poultix.server.entities.Farm;
import com.poultix.server.entities.enums.FarmStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    List<Farm> findByOwner_Id(UUID ownerId);
    
    List<Farm> findByAssignedVeterinary_Id(UUID veterinaryId);
    
    List<Farm> findByHealthStatus(FarmStatus healthStatus);
    
    List<Farm> findByIsActive(Boolean isActive);
    
    List<Farm> findByOwner_IdAndIsActive(UUID ownerId, Boolean isActive);
}
