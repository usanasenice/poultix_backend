package com.poultix.server.mappers;

import com.poultix.server.dto.FarmDTO;
import com.poultix.server.entities.Farm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    FarmDTO toDTO(Farm farm);

    Farm toEntity(FarmDTO farmDTO);

    List<FarmDTO> toDTOs(List<Farm> farms);

    List<Farm> toEntities(List<FarmDTO> farmDTOs);

}
