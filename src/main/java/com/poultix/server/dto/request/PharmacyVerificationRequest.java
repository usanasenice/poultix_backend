package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyVerificationRequest {
    
    @NotNull(message = "Pharmacy ID is required")
    private UUID pharmacyId;
    
    @NotNull(message = "Verification status is required")
    private VerificationStatus verificationStatus;
    
    private String adminComments;
    
    private List<String> missingDocuments;
    
    @NotNull(message = "Reviewer name is required")
    private String reviewerName;
}
