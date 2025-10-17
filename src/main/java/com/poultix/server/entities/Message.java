package com.poultix.server.entities;

import com.poultix.server.entities.embeddables.Reaction;
import com.poultix.server.entities.enums.MessageStatus;
import com.poultix.server.entities.enums.MessageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotNull(message = "Sender is required")
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    
    @NotNull(message = "Receiver is required")
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    
    @NotBlank(message = "Content is required")
    @Column(nullable = false)
    private String content;
    
    @Enumerated(EnumType.STRING)
    private MessageType type = MessageType.TEXT;
    
    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.DELIVERED;
    
    private Boolean edited = false;
    
    @Column(name = "file_name")
    private String fileName;
    
    @ElementCollection
    @CollectionTable(name = "message_reactions", joinColumns = @JoinColumn(name = "message_id"))
    private List<Reaction> reactions = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "reply_to_id")
    private Message replyTo;
    
    private LocalDateTime timestamp = LocalDateTime.now();
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
