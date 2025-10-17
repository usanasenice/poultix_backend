package com.poultix.server.service;

import com.poultix.server.dto.request.HelpSupportCreateRequest;
import com.poultix.server.dto.HelpSupportDTO;
import com.poultix.server.entities.HelpSupport;
import com.poultix.server.entities.User;
import com.poultix.server.entities.enums.HelpSupportPriority;
import com.poultix.server.entities.enums.HelpSupportStatus;
import com.poultix.server.entities.enums.HelpSupportType;
import com.poultix.server.mappers.HelpSupportMapper;
import com.poultix.server.repository.HelpSupportRepository;
import com.poultix.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional 
public class HelpSupportService { 
    
    private final HelpSupportRepository helpSupportRepository;
    private final HelpSupportMapper helpSupportMapper;
    private final UserRepository userRepository;
    
    public HelpSupportDTO createTicket(HelpSupportCreateRequest request, UUID userId) {
        // Create new help support ticket
        HelpSupport ticket = new HelpSupport();
        ticket.setTitle(request.getTitle());
        ticket.setBody(request.getBody());
        
        // Convert string type to enum (basic conversion - enhance as needed)
        try {
            ticket.setType(request.getType());
        } catch (IllegalArgumentException e) {
            ticket.setType(HelpSupportType.GENERAL); // Default fallback
        }
        
        // Set defaults
        ticket.setPriority(HelpSupportPriority.MEDIUM);
        ticket.setStatus(HelpSupportStatus.OPEN);
        
        // Fetch and set sender
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        ticket.setSender(sender);
        
        HelpSupport savedTicket = helpSupportRepository.save(ticket);
        return helpSupportMapper.toDTO(savedTicket);
    }
    
    @Transactional(readOnly = true)
    public HelpSupportDTO getTicketById(UUID id) {
        HelpSupport ticket = helpSupportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        return helpSupportMapper.toDTO(ticket);
    }
    
    @Transactional(readOnly = true)
    public List<HelpSupportDTO> getAllTickets() {
        return helpSupportRepository.findAll().stream()
                .map(helpSupportMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<HelpSupportDTO> getTicketsBySender(UUID senderId) {
        return helpSupportRepository.findBySenderId(senderId).stream()
                .map(helpSupportMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteTicket(UUID id) {
        helpSupportRepository.deleteById(id);
    }
}
