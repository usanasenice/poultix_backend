package com.poultix.server.repository;

import com.poultix.server.entities.Schedule;
import com.poultix.server.entities.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findByFarmer_Id(UUID farmerId);
    
    List<Schedule> findByVeterinary_Id(UUID veterinaryId);
    
    List<Schedule> findByStatus(ScheduleStatus status);
    
    List<Schedule> findByScheduledDate(LocalDate scheduledDate);
    
    List<Schedule> findByFarmer_IdAndStatus(UUID farmerId, ScheduleStatus status);
    
    List<Schedule> findByVeterinary_IdAndStatus(UUID veterinaryId, ScheduleStatus status);
    
    List<Schedule> findByVeterinary_IdAndScheduledDate(UUID veterinaryId, LocalDate scheduledDate);
    
    List<Schedule> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);
}
