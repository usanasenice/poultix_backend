package com.poultix.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.poultix.server.constants.LocationConstants;
import com.poultix.server.dto.DocumentUploadDTO;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    
    @Value("${file.upload.directory:uploads}")
    private String uploadDirectory;
    
    @Value("${file.upload.max-size:10485760}") // 10MB default
    private long maxFileSize;
    
    private final HttpServletRequest request;
    
    /**
     * Upload a pharmacy document with Rwanda-specific validation
     */
    public DocumentUploadDTO uploadPharmacyDocument(MultipartFile file, String documentType, UUID pharmacyId, String uploadedBy) throws IOException {
        validatePharmacyDocument(file, documentType);
        
        String fileName = generateFileName(file.getOriginalFilename());
        String folder = "pharmacy-documents/" + pharmacyId + "/" + documentType.toLowerCase();
        String filePath = uploadFileWithCustomName(file, folder, fileName);
        
        return DocumentUploadDTO.builder()
                .pharmacyId(pharmacyId)
                .documentType(documentType)
                .fileName(file.getOriginalFilename())
                .filePath(filePath)
                .fileSize(formatFileSize(file.getSize()))
                .contentType(file.getContentType())
                .uploadedAt(LocalDateTime.now())
                .uploadedBy(uploadedBy)
                .isRequired(LocationConstants.REQUIRED_DOCUMENTS.contains(documentType))
                .status("PENDING")
                .build();
    }
    
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds maximum allowed size");
        }
        
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDirectory, folder);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Generate unique filename
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Save the file
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);
        
        // Return the accessible URL
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl + "/files/" + folder + "/" + uniqueFileName;
    }
    
    public String uploadProfileImage(MultipartFile file) throws IOException {
        validateImageFile(file);
        return uploadFile(file, "profiles");
    }
    
    public String uploadAttachment(MultipartFile file) throws IOException {
        validateFileType(file);
        return uploadFile(file, "attachments");
    }
    
    private String getBaseUrl() {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(scheme).append("://").append(serverName);
        
        if ((scheme.equals("http") && serverPort != 80) || 
            (scheme.equals("https") && serverPort != 443)) {
            baseUrl.append(":").append(serverPort);
        }
        
        return baseUrl.toString();
    }
    
    private void validateImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }
        
        // Additional image validation can be added here
        String[] allowedTypes = {"image/jpeg", "image/png", "image/gif", "image/webp"};
        boolean isValidType = false;
        for (String type : allowedTypes) {
            if (type.equals(contentType)) {
                isValidType = true;
                break;
            }
        }
        
        if (!isValidType) {
            throw new IllegalArgumentException("Unsupported image format");
        }
    }
    
    private void validateFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("File type could not be determined");
        }
        
        // Define allowed file types
        String[] allowedTypes = {
            "image/jpeg", "image/png", "image/gif", "image/webp",
            "application/pdf", "text/plain", "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        };
        
        boolean isValidType = false;
        for (String type : allowedTypes) {
            if (type.equals(contentType)) {
                isValidType = true;
                break;
            }
        }
        
        if (!isValidType) {
            throw new IllegalArgumentException("File type not allowed");
        }
    }
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    private String uploadFileWithCustomName(MultipartFile file, String folder, String fileName) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds maximum allowed size");
        }
        
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDirectory, folder);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Save the file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        
        // Return the accessible URL
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl + "/files/" + folder + "/" + fileName;
    }
    
    private void validatePharmacyDocument(MultipartFile file, String documentType) {
        if (!LocationConstants.REQUIRED_DOCUMENTS.contains(documentType)) {
            throw new IllegalArgumentException("Invalid document type: " + documentType);
        }
        
        // Validate file types based on document type
        List<String> allowedTypes = Arrays.asList("application/pdf", "image/jpeg", "image/png", "image/jpg");
        String contentType = file.getContentType();
        
        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Allowed: PDF, JPEG, PNG");
        }
        
        // Check file size (max 5MB for documents)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("Document file size cannot exceed 5MB");
        }
    }
    
    private String generateFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }
    
    private String formatFileSize(long sizeInBytes) {
        if (sizeInBytes < 1024) {
            return sizeInBytes + " B";
        } else if (sizeInBytes < 1024 * 1024) {
            return String.format("%.1f KB", sizeInBytes / 1024.0);
        } else {
            return String.format("%.1f MB", sizeInBytes / (1024.0 * 1024.0));
        }
    }
    
    public void deleteFile(String fileName, String folder) throws IOException {
        Path filePath = Paths.get(uploadDirectory, folder, fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
}
