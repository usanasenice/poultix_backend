package com.poultix.server.entities;

import com.poultix.server.entities.embeddables.VeterinaryRates;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "veterinaries")
public class Veterinary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "service_radius")
    private Double serviceRadius; 
    
    @Embedded
    private VeterinaryRates rates;


    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Rating must not exceed 5")
    private Double rating = 0.0;
    
    @Column(name = "total_visits")
    private Integer totalVisits = 0;
    
    @Column(name = "join_date")
    private LocalDateTime joinDate = LocalDateTime.now();
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @ElementCollection
    @CollectionTable(name = "veterinary_farms_managed", joinColumns = @JoinColumn(name = "veterinary_id"))
    @Column(name = "farm_id")
    private List<UUID> farmManaged = new ArrayList<>();
    

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
