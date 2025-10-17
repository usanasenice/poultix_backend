package com.poultix.server.repository;

import com.poultix.server.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, UUID> {
    
    Optional<Vaccine> findByName(String name);
    
    List<Vaccine> findByType(String type);
    
    List<Vaccine> findByTargetDisease(String targetDisease);
    
    List<Vaccine> findByPrescriptionRequired(boolean prescriptionRequired);
    
    @Query("SELECT v FROM Vaccine v WHERE v.name LIKE %:keyword% OR v.targetDisease LIKE %:keyword%")
    List<Vaccine> findByKeyword(@Param("keyword") String keyword);
    
    boolean existsByName(String name);
}
