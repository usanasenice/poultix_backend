package com.poultix.server.repository;

import com.poultix.server.entities.HelpSupport;
import com.poultix.server.entities.enums.SupportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HelpSupportRepository extends JpaRepository<HelpSupport, UUID> {
    List<HelpSupport> findBySenderId(UUID senderId);
    
    List<HelpSupport> findByType(SupportType type);
    
    List<HelpSupport> findBySenderIdOrderByCreatedAtDesc(UUID senderId);
    
    List<HelpSupport> findAllByOrderByCreatedAtDesc();
}
