package com.poultix.server.dto;

import com.poultix.server.entities.enums.MessageStatus;
import com.poultix.server.entities.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    private UUID id;
    private UserDTO sender;
    private UserDTO receiver;
    private String content;
    private MessageType type;
    private MessageStatus status;
    private Boolean edited;
    private String fileName;
    private List<ReactionDTO> reactions;
    private MessageDTO replyTo;
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
