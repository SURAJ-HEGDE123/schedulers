package com.example.scheduler.repository;

import com.example.scheduler.entity.SchedularLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulerLogRepository  extends JpaRepository<SchedularLog, Long> {
    List<SchedularLog> findTop20ByOrderByStartTimeDesc();

    List<SchedularLog>findByStatusInOrderByStartTimeDesc(Iterable<String>statuses);

    List<SchedularLog>findBySchedulerNameContainingIgnoreCase(String name);

    Optional<SchedularLog>findById(Long id);

    List<SchedularLog>findByStatusIgnoreCase(String status);

    List<SchedularLog>findByStartTimeBetween(LocalDateTime start,LocalDateTime end);

     long countByStatusIgnoreCase(String status);


}
