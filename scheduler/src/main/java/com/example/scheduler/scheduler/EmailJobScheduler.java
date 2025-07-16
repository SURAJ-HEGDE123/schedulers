package com.example.scheduler.scheduler;

import com.example.scheduler.service.SchedulerJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailJobScheduler {

    private final SchedulerJobService schedulerJobService;

    @Scheduled(fixedRate = 60000)
    public void runEmailJob() {
        schedulerJobService.executeWithRetry("EmailJobScheduler","TaskType=Email", () -> {
            if (Math.random() > 0.7) {
                throw new RuntimeException("Email job failed");
            }
            System.out.println("Email job completed successfully");
        });
    }

}