package com.poultix.server.mappers;

import com.poultix.server.dto.UserDTO;
import com.poultix.server.entities.User;

import java.util.List;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDTO(User user);
    List<User> toEntities(List<UserDTO> userDTOs);
    List<UserDTO> toDTOs(List<User> users);
}
