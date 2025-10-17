package com.poultix.server.entities;

import com.poultix.server.entities.embeddables.Facilities;
import com.poultix.server.entities.embeddables.Location;
import com.poultix.server.entities.embeddables.Livestock;
import com.poultix.server.entities.enums.FarmStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "farms")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank(message = "Farm name is required")
    @Column(nullable = false)
    private String name;
    
    @NotNull(message = "Owner is required")
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User  owner;
    
    @Embedded
    private Location location;
 
    @Embedded
    private Livestock livestock;
    
    @Embedded
    private Facilities facilities;
    
    
    @ManyToOne
    @JoinColumn(name = "assigned_veterinary_id")
    private User assignedVeterinary;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "health_status")
    private FarmStatus healthStatus = FarmStatus.GOOD;
    
    @Column(name = "last_inspection")
    private LocalDate lastInspection;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
