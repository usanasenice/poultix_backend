package com.poultix.server.service;

import com.poultix.server.dto.ScheduleDTO;
import com.poultix.server.dto.request.ScheduleCreateRequest;
import com.poultix.server.dto.request.ScheduleUpdateRequest;
import com.poultix.server.entities.Schedule;
import com.poultix.server.entities.User;
import com.poultix.server.entities.enums.ScheduleStatus;
import com.poultix.server.mappers.ScheduleMapper;
import com.poultix.server.repository.ScheduleRepository;
import com.poultix.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final UserRepository userRepository;

    /**
     * Create a new schedule
     */
    public ScheduleDTO createSchedule(ScheduleCreateRequest request, UUID createdBy) {
        // Create new schedule
        Schedule schedule = new Schedule();
        schedule.setTitle(request.getTitle());
        schedule.setDescription(request.getDescription());
        schedule.setType(request.getType());
        schedule.setScheduledDate(request.getScheduledDate());
        schedule.setPriority(request.getPriority());

        // Fetch and set creator (farmer)
        User creator = userRepository.findById(createdBy)
                .orElseThrow(() -> new IllegalArgumentException("Creator not found with id: " + createdBy));
        schedule.setFarmer(creator);
        schedule.setCreatedBy(creator);

        // Fetch and set veterinary
        User veterinary = userRepository.findById(request.getVeterinary().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Veterinary not found with id: " + request.getVeterinary().getId()));
        schedule.setVeterinary(veterinary);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDTO(savedSchedule);
    }

    /**
     * Get schedule by ID
     */
    @Transactional(readOnly = true)
    public ScheduleDTO getScheduleById(UUID id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
        return scheduleMapper.toDTO(schedule);
    }

    /**
     * Get all schedules
     */
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schedules by farmer ID
     */
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getSchedulesByFarmerId(UUID farmerId) {
        return scheduleRepository.findByFarmer_Id(farmerId).stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schedules by veterinary ID
     */
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getSchedulesByVeterinaryId(UUID veterinaryId) {
        return scheduleRepository.findByVeterinary_Id(veterinaryId).stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schedules by status
     */
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getSchedulesByStatus(ScheduleStatus status) {
        return scheduleRepository.findByStatus(status).stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schedules by date
     */
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getSchedulesByDate(LocalDate date) {
        return scheduleRepository.findByScheduledDate(date).stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schedules by date range
     */
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        return scheduleRepository.findByScheduledDateBetween(startDate, endDate).stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get veterinary schedules for a specific date
     */
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getVeterinarySchedulesByDate(UUID veterinaryId, LocalDate date) {
        return scheduleRepository.findByVeterinary_IdAndScheduledDate(veterinaryId, date).stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update schedule
     */
    public ScheduleDTO updateSchedule(UUID id, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));

        // Update schedule fields manually
        if (request.getTitle() != null) {
            schedule.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            schedule.setDescription(request.getDescription());
        }
        if (request.getScheduledDate() != null) {
            schedule.setScheduledDate(request.getScheduledDate());
        }
     
        if (request.getStatus() != null) {
            schedule.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            schedule.setPriority(request.getPriority());
        }

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDTO(updatedSchedule);
    }

    /**
     * Update schedule status
     */
    public ScheduleDTO updateScheduleStatus(UUID id, ScheduleStatus status) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));

        schedule.setStatus(status);
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDTO(updatedSchedule);
    }

    /**
     * Complete schedule
     */
    public ScheduleDTO completeSchedule(UUID id) {
        return updateScheduleStatus(id, ScheduleStatus.COMPLETED);
    }

    /**
     * Cancel schedule
     */
    public ScheduleDTO cancelSchedule(UUID id) {
        return updateScheduleStatus(id, ScheduleStatus.CANCELLED);
    }

    /**
     * Delete schedule
     */
    public void deleteSchedule(UUID id) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("Schedule not found with id: " + id);
        }
        scheduleRepository.deleteById(id);
    }
}
