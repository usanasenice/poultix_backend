package com.poultix.server.repository;

import com.poultix.server.entities.Message;
import com.poultix.server.entities.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findBySender_Id(UUID senderId);
    
    List<Message> findByReceiver_Id(UUID receiverId);
    
    List<Message> findBySender_IdAndReceiver_Id(UUID senderId, UUID receiverId);
    
    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR (m.sender.id = :userId2 AND m.receiver.id = :userId1) ORDER BY m.timestamp ASC")
    List<Message> findConversationBetweenUsers(UUID userId1, UUID userId2);
    
    List<Message> findByStatus(MessageStatus status);
    
    List<Message> findByReplyTo_Id(UUID replyToId);
}
