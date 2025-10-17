package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUpdateRequest {
    private String content;
    private MessageStatus status;
}
