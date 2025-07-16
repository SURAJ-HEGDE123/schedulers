package com.example.scheduler.scheduler;


import com.example.scheduler.service.SchedulerJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final SchedulerJobService schedulerJobService;

@Scheduled(fixedRate = 60000)
    public void runNotificationJob(){

        schedulerJobService.executeWithRetry("NotificationJobScheduler","TaskType=Notification",()->{
            if (Math.random() > 0.7){
                throw new RuntimeException("Notification job failed");
            }
            System.out.println("Notification job completed successfully");
        });
    }

}
