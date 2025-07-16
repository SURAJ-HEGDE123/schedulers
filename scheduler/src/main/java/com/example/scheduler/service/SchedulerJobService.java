package com.example.scheduler.service;


import com.example.scheduler.entity.SchedularLog;
import com.example.scheduler.repository.SchedulerLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class SchedulerJobService {

    private final SchedulerLogRepository logRepo;
    private final NotificationService notificationService;

    @Transactional
    public void executeWithRetry(String schedulerName, String inputData, Runnable jobLogic) {
        int retryCount = 0;
        boolean success = false;

        while (retryCount < 3 && !success) {
            LocalDateTime start = LocalDateTime.now();
            try {
                // Simulate failure for testing
                if (Math.random() > 0.7) {
                    throw new RuntimeException("simulated failure");
                }

                // Log success
                logRepo.save(SchedularLog.builder()
                        .schedulerName(schedulerName)
                        .startTime(start)
                        .endTime(LocalDateTime.now())
                        .status("SUCCESS")
                        .retryCount(retryCount)
                        .inputData(inputData)
                        .build());

                success = true;
            } catch (Exception ex) {
                retryCount++;

                // Log failure or retrying
                logRepo.save(SchedularLog.builder()
                        .schedulerName(schedulerName)
                        .startTime(start)
                        .endTime(LocalDateTime.now())
                        .status(retryCount == 3 ? "FAILED" : "RETRYING")
                        .retryCount(retryCount)
                        .errorMessage(ex.getMessage())
                        .inputData(inputData)
                        .build());

                // Send email on final failure
                if (retryCount == 3) {
                    notificationService.sendFailureAlert(schedulerName, ex.getMessage());
                }

                // Wait before retry
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}