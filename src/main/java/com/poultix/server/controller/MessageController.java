package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.dto.request.MessageCreateRequest;
import com.poultix.server.dto.MessageDTO;
import com.poultix.server.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    
    private final MessageService messageService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<MessageDTO>> sendMessage(@Valid @RequestBody MessageCreateRequest request) {
        MessageDTO response = messageService.sendMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Message sent successfully", response));
    }
    
    @GetMapping("/conversation")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getConversation(@RequestParam UUID user1Id, @RequestParam UUID user2Id) {
        List<MessageDTO> messages = messageService.getConversation(user1Id, user2Id);
        return ResponseEntity.ok(ApiResponse.success("Conversation retrieved successfully", messages));
    }
    
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getMessagesBySender(@PathVariable UUID senderId) {
        List<MessageDTO> messages = messageService.getMessagesBySender(senderId);
        return ResponseEntity.ok(ApiResponse.success("Messages retrieved successfully", messages));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable UUID id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success("Message deleted successfully", null));
    }
}
