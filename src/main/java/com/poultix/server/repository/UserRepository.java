package com.poultix.server.repository;

import com.poultix.server.entities.User;
import com.poultix.server.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<User> findByRole(UserRole role);
    
    List<User> findByIsActive(Boolean isActive);
    
    List<User> findByRoleAndIsActive(UserRole role, Boolean isActive);
}
