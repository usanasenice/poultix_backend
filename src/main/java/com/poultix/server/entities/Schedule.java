package com.poultix.server.entities;

import com.poultix.server.entities.enums.SchedulePriority;
import com.poultix.server.entities.enums.ScheduleStatus;
import com.poultix.server.entities.enums.ScheduleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotNull(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    private String description;
    
    @NotNull(message = "Schedule type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleType type;
    
    @NotNull(message = "Farmer is required")
    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private User farmer;
    
    @NotNull(message = "Veterinary is required")
    @ManyToOne
    @JoinColumn(name = "veterinary_id")
    private User veterinary;
    
    @NotNull(message = "Scheduled date is required")
    @Column(nullable = false, name = "scheduled_date")
    private LocalDate scheduledDate;
    
    
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status = ScheduleStatus.SCHEDULED;
    
    @Enumerated(EnumType.STRING)
    private SchedulePriority priority = SchedulePriority.MEDIUM;

    @NotNull(message = "Created by user is required")
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
