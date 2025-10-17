package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateRequest {
    @NotNull(message = "Receiver ID is required")
    private UUID receiverId;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private MessageType type;
    private String fileName;
    private UUID replyToId;
}
