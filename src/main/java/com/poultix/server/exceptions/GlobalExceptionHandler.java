package com.poultix.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.poultix.server.dto.ApiResponse;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid data");
        return ResponseEntity.ok(new ApiResponse<>(false, errorMessage, "Invalid Data"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Verification failed", null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "User not found", null));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Invalid credentials", null));
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ApiResponse<?>> handleEmailNotVerifiedException(EmailNotVerifiedException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Email not verified", null));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Email already exists", null));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Invalid token", null));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Username not found", null));
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleGroupNotFoundException(GroupNotFoundException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Group not found", null));
    }

    @ExceptionHandler(GroupMessageNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleGroupMessageNotFoundException(GroupMessageNotFoundException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Message not found", null));
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotificationNotFoundException(NotificationNotFoundException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Notification not found", null));
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleMessageNotFoundException(MessageNotFoundException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "Message not found", null));
    }

    @ExceptionHandler(UserSettingsNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserSettingsNotFoundException(UserSettingsNotFoundException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "User settings not found", null));
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ApiResponse<?>> handleSystemExceptions(SystemException ex) {
        return ResponseEntity.ok(new ApiResponse<>(false, "System error", null));
    }

    // Rwanda Pharmacy specific exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }
    
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse<?>> handleIOException(IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "File operation failed: " + ex.getMessage(), null));
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<?>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "File size exceeds maximum allowed size", null));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Internal server error", null));
    }

}