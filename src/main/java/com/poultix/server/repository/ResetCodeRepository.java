package com.poultix.server.repository;

import com.poultix.server.entities.ResetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResetCodeRepository extends JpaRepository<ResetCode, UUID> {
    Optional<ResetCode> findByUserIdAndCode(UUID userId, String code);
    
    List<ResetCode> findByUserId(UUID userId);
    
    void deleteByUserId(UUID userId);
    
    void deleteByTimeStampBefore(LocalDateTime expiryTime);
}
