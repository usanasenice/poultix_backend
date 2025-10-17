package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.HelpSupportPriority;
import com.poultix.server.entities.enums.HelpSupportType;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelpSupportCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Body is required")
    private String body;
    
    @NotBlank(message = "Type is required")
    private HelpSupportType type;

    @NotBlank(message = "Priority is required")
    private HelpSupportPriority priority;
}
