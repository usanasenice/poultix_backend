package com.poultix.server.mappers;

import com.poultix.server.dto.PharmacyDTO;
import com.poultix.server.entities.Pharmacy;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PharmacyMapper {
    Pharmacy toEntity(PharmacyDTO pharmacyDTO);
    PharmacyDTO toDTO(Pharmacy pharmacy);
    List<Pharmacy> toEntities(List<PharmacyDTO> pharmacyDTOs);
    List<PharmacyDTO> toDTOs(List<Pharmacy> pharmacies);
}
