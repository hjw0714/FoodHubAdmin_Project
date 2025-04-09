package com.application.foodhubAdmin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class StatsSchedulerService {

    private final UserMsService userMsService;

//    @Scheduled(cron = "0 0 0 * * *")
//    public void runStats() {
//        LocalDate yesterday = LocalDate.now().minusDays(1);
//        userMsService.updateUserStats(yesterday);
//    }

    @Scheduled(cron = "0 56 17 * * *")
    public void runStats() {
        LocalDate today = LocalDate.now();
        if (today.equals(LocalDate.of(2025, 4, 9))) { // 오늘 날짜
            LocalDate yesterday = today.minusDays(1);
            userMsService.updateUserStats(yesterday);
        }
    }


}
