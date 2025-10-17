package com.poultix.server.mappers;

import com.poultix.server.dto.HelpSupportDTO;
import com.poultix.server.entities.HelpSupport;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HelpSupportMapper {
  HelpSupport toEntity(HelpSupportDTO helpSupportDTO);
  HelpSupportDTO toDTO(HelpSupport helpSupport); 
  List<HelpSupport> toEntities(List<HelpSupportDTO> helpSupportDTOs);
  List<HelpSupportDTO> toDTOs(List<HelpSupport> helpSupports);
}
