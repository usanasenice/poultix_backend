package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.dto.request.HelpSupportCreateRequest;
import com.poultix.server.dto.HelpSupportDTO;
import com.poultix.server.dto.UserDTO;
import com.poultix.server.service.HelpSupportService;
import com.poultix.server.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/support")
@RequiredArgsConstructor
public class HelpSupportController {
    
    private final HelpSupportService helpSupportService;
    private final UserService userService;

    public UUID getUserId(Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return user.getId();
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<HelpSupportDTO>> createTicket(@Valid @RequestBody HelpSupportCreateRequest request, Principal principal) {
        UUID userId = getUserId(principal);
        HelpSupportDTO response = helpSupportService.createTicket(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Support ticket created successfully", response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HelpSupportDTO>> getTicketById(@PathVariable UUID id) {
        HelpSupportDTO response = helpSupportService.getTicketById(id);
        return ResponseEntity.ok(ApiResponse.success("Support ticket retrieved successfully", response));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<HelpSupportDTO>>> getAllTickets() {
        List<HelpSupportDTO> tickets = helpSupportService.getAllTickets();
        return ResponseEntity.ok(ApiResponse.success("Support tickets retrieved successfully", tickets));
    }
    
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<HelpSupportDTO>>> getTicketsByUser(Principal principal) {
        UUID userId = getUserId(principal);
        List<HelpSupportDTO> tickets = helpSupportService.getTicketsBySender(userId);
        return ResponseEntity.ok(ApiResponse.success("Support tickets retrieved successfully", tickets));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTicket(@PathVariable UUID id) {
        helpSupportService.deleteTicket(id);
        return ResponseEntity.ok(ApiResponse.success("Support ticket deleted successfully", null));
    }
}
