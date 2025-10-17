package com.poultix.server.mappers;

import com.poultix.server.dto.VeterinaryDTO;
import com.poultix.server.entities.Veterinary;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VeterinaryMapper {
    Veterinary toEntity(VeterinaryDTO veterinaryDTO);
    VeterinaryDTO toDTO(Veterinary veterinary);
    List<Veterinary> toEntities(List<VeterinaryDTO> veterinaryDTOs);
    List<VeterinaryDTO> toDTOs(List<Veterinary> veterinarys);
}
