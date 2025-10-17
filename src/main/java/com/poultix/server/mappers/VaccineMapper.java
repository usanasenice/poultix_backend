package com.poultix.server.mappers;

import com.poultix.server.dto.VaccineDTO;
import com.poultix.server.entities.Vaccine;

import java.util.List;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VaccineMapper {
    Vaccine toEntity(VaccineDTO vaccineDTO);
    VaccineDTO toDTO(Vaccine vaccine);
    List<Vaccine> toEntities(List<VaccineDTO> vaccineDTOs);
    List<VaccineDTO> toDTOs(List<Vaccine> vaccines);
}
