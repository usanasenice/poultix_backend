package com.poultix.server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.poultix.server.entities.enums.HelpSupportPriority;
import com.poultix.server.entities.enums.HelpSupportStatus;
import com.poultix.server.entities.enums.HelpSupportType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "help_support")
public class HelpSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Body is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;
    
    @NotNull(message = "Sender is required")
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    
    @NotNull(message = "Type is required")
    @Column(nullable = false)
    private HelpSupportType type;
    
    @NotNull(message = "Priority is required")
    @Column(nullable = false)
    private HelpSupportPriority priority;
    
    @NotNull(message = "Status is required")
    @Column(nullable = false)
    private HelpSupportStatus status;
    
    private LocalDateTime time = LocalDateTime.now();
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
