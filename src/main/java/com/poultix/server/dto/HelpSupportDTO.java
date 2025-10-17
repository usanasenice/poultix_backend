package com.poultix.server.dto;

import com.poultix.server.entities.enums.SupportPriority;
import com.poultix.server.entities.enums.SupportStatus;
import com.poultix.server.entities.enums.SupportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpSupportDTO {
    private UUID id;
    private String title;
    private String body;
    private UserDTO sender;
    private SupportType type;
    private SupportPriority priority;
    private SupportStatus status;
    private LocalDateTime time;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
