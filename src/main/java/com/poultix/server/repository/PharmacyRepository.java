package com.poultix.server.repository;

import com.poultix.server.entities.Pharmacy;
import com.poultix.server.entities.User;
import com.poultix.server.entities.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID> {
    List<Pharmacy> findByIsOpen(Boolean isOpen);
    
    List<Pharmacy> findByRatingGreaterThanEqual(Double rating);
    
    List<Pharmacy> findByIsOpenOrderByRatingDesc(Boolean isOpen);
    
    List<Pharmacy> findAllByOrderByNameAsc();
    
    // Rwanda-specific queries
    boolean existsByLicenseNumber(String licenseNumber);
    
    List<Pharmacy> findByVerificationStatus(VerificationStatus verificationStatus);
    
    List<Pharmacy> findByProvince(String province);
    
    List<Pharmacy> findByProvinceAndDistrict(String province, String district);
    
    // User-based queries
    boolean existsByOwner(User owner);
    
    Optional<Pharmacy> findByOwner(User owner);
}
