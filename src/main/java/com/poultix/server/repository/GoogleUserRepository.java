package com.poultix.server.repository;

import com.poultix.server.entities.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoogleUserRepository extends JpaRepository<GoogleUser, UUID> {
    Optional<GoogleUser> findByGoogleId(String googleId);
    
    Optional<GoogleUser> findByEmail(String email);
    
    boolean existsByGoogleId(String googleId);
    
    boolean existsByEmail(String email);
}
