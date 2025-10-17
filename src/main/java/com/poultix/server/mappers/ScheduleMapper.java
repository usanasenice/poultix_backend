package com.poultix.server.mappers;

import com.poultix.server.dto.ScheduleDTO;
import com.poultix.server.entities.Schedule;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    
    Schedule toEntity(ScheduleDTO scheduleDTO);
    ScheduleDTO toDTO(Schedule schedule);
    List<Schedule> toEntities(List<ScheduleDTO> scheduleDTOs);
    List<ScheduleDTO> toDTOs(List<Schedule> schedules);
}
