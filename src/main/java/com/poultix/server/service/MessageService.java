package com.poultix.server.service;

import com.poultix.server.dto.request.MessageCreateRequest;
import com.poultix.server.dto.MessageDTO;
import com.poultix.server.entities.Message;
import com.poultix.server.entities.User;
import com.poultix.server.mappers.MessageMapper;
import com.poultix.server.repository.MessageRepository;
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
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;
    
    public MessageDTO sendMessage(MessageCreateRequest request) {
        // TODO: SenderId should come from authentication context or be passed as parameter
        // For now, this will fail without a valid senderId
        throw new IllegalArgumentException("Sender ID is required but not provided in current implementation");
    }
    
    // Alternative method with senderId parameter
    public MessageDTO sendMessage(MessageCreateRequest request, UUID senderId) {
        // Create new message
        Message message = new Message();
        message.setContent(request.getContent());
        message.setType(request.getType());
        message.setFileName(request.getFileName());
        
        // Fetch and set sender
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found with id: " + senderId));
        message.setSender(sender);
        
        // Fetch and set receiver
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found with id: " + request.getReceiverId()));
        message.setReceiver(receiver);
        
        // Handle reply-to if provided
        if (request.getReplyToId() != null) {
            Message replyTo = messageRepository.findById(request.getReplyToId())
                    .orElseThrow(() -> new IllegalArgumentException("Reply-to message not found"));
            message.setReplyTo(replyTo);
        }
        
        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDTO(savedMessage);
    }
    
    @Transactional(readOnly = true)
    public List<MessageDTO> getConversation(UUID user1Id, UUID user2Id) {
        return messageRepository.findBySender_IdAndReceiver_Id(user1Id, user2Id).stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<MessageDTO> getMessagesBySender(UUID senderId) {
        return messageRepository.findBySender_Id(senderId).stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteMessage(UUID id) {
        messageRepository.deleteById(id);
    }
}
