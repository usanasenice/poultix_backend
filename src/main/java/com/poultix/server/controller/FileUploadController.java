package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {
    
    private final FileUploadService fileUploadService;
    
    @PostMapping("/profile-image")
    public ResponseEntity<ApiResponse<String>> uploadProfileImage(
            @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileUploadService.uploadProfileImage(file);
            return ResponseEntity.ok(ApiResponse.success("Profile image uploaded successfully", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Failed to upload profile image: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/attachment")
    public ResponseEntity<ApiResponse<String>> uploadAttachment(
            @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileUploadService.uploadAttachment(file);
            return ResponseEntity.ok(ApiResponse.success("Attachment uploaded successfully", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Failed to upload attachment: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/document")
    public ResponseEntity<ApiResponse<String>> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", defaultValue = "documents") String folder) {
        try {
            String fileUrl = fileUploadService.uploadFile(file, folder);
            return ResponseEntity.ok(ApiResponse.success("Document uploaded successfully", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Failed to upload document: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @DeleteMapping("/{folder}/{fileName}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(
            @PathVariable String folder,
            @PathVariable String fileName) {
        try {
            fileUploadService.deleteFile(fileName, folder);
            return ResponseEntity.ok(ApiResponse.success("File deleted successfully", null));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Failed to delete file: " + e.getMessage()));
        }
    }
}
