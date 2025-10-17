package com.poultix.server.dto;

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
public class DocumentUploadDTO {
    private UUID pharmacyId;
    private String documentType;
    private String fileName;
    private String filePath;
    private String fileSize;
    private String contentType;
    private LocalDateTime uploadedAt;
    private String uploadedBy;
    private Boolean isRequired;
    private String status; // PENDING, APPROVED, REJECTED
}
