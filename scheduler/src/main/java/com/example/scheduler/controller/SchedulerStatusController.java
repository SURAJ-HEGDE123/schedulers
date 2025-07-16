package com.example.scheduler.controller;


import com.example.scheduler.entity.SchedularLog;
import com.example.scheduler.repository.SchedulerLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SchedulerStatusController {

    private final SchedulerLogRepository logRepo;

    @GetMapping("/status")
    public List<SchedularLog> getLatestRuns() {

        return logRepo.findTop20ByOrderByStartTimeDesc();
    }
    @GetMapping("/status/incomplete")
    public List<SchedularLog>getIncompleteRuns(){
        return logRepo.findByStatusInOrderByStartTimeDesc(Set.of("FAILED","RETRYING"));
    }


    @GetMapping("/search")
    public List<SchedularLog>searchLogs(@RequestParam String keyword){
        return logRepo.findBySchedulerNameContainingIgnoreCase(keyword);
    }


    @GetMapping("/status/{id}")
    public ResponseEntity<SchedularLog>getById(@PathVariable Long id){
        return logRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/status/success")
    public List<SchedularLog> getSuccessLogs(){
        return logRepo.findByStatusIgnoreCase("SUCCESS");
    }

    @GetMapping("/status/between")
    public List<SchedularLog>getLogsBetweenDates(@RequestParam String start, @RequestParam String end){
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return logRepo.findByStartTimeBetween(startDate,endDate);
    }

    @GetMapping("/status/fail-count")
    public long getTotalFailedSchedulers() {
        return logRepo.countByStatusIgnoreCase("FAILED");
    }


    @GetMapping("/status/sort-by-retries")
    public List<SchedularLog>getLogsSortedByRetryCount(){
        return logRepo.findAll(Sort.by(Sort.Direction.DESC,"retryCount"));
    }

}


