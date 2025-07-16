package com.example.scheduler.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="scheduler_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchedularLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schedulerName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String status;

    private int retryCount;

    @Column(length = 1000)
    private String errorMessage;

    @Column(length = 1000, nullable = false)
    private String inputData;

}
