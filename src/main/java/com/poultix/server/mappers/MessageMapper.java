package com.poultix.server.mappers;

import com.poultix.server.dto.MessageDTO;
import com.poultix.server.entities.Message;

import java.util.List;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDTO toDTO(Message message);

    Message toEntity(MessageDTO messageDTO);

    List<MessageDTO> toDTOs(List<Message> messages);

    List<Message> toEntities(List<MessageDTO> messageDTOs);

}
