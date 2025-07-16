package com.example.scheduler.scheduler;


import com.example.scheduler.entity.SchedularLog;
import com.example.scheduler.repository.SchedulerLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MyJobScheduler {

    private final SchedulerLogRepository logRepo;

    @Scheduled(fixedRate = 60000)
    public void runJob() {
        String schedulerName = "MyJobScheduler";
        String inputData = "TaskType=GenerateReport, Date="+
                LocalDateTime.now().toLocalDate();
        int retryCount = 0;
        boolean success = false;

        while (retryCount < 3 && !success) {
            LocalDateTime start = LocalDateTime.now();
            try {

                if (Math.random() > 0.7) {
                    throw new RuntimeException("Simulated failure");
                }

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
                logRepo.save(SchedularLog.builder()
                        .schedulerName(schedulerName)
                        .startTime(start)
                        .endTime(LocalDateTime.now())
                        .status(retryCount == 3 ? "FAILED" : "RETRYING")
                        .retryCount(retryCount)
                        .inputData(inputData)
                        .errorMessage(ex.getMessage())
                        .build());

                if (retryCount == 3) {
                    sendFailureNotification(schedulerName, ex.getMessage());
                }
            }
        }
    }

    private void sendFailureNotification(String name, String message) {
        System.out.println("ALERT: " + name + " failed. Reason: " + message);

    }
}
