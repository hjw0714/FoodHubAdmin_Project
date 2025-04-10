package com.application.foodhubAdmin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class StatsSchedulerService {

    private final UserMsService userMsService;

//    @Scheduled(cron = "0 0 0 * * *")
//    @Transactional
//    public void runStats() {
//        LocalDate yesterday = LocalDate.now().minusDays(1);
//        userMsService.updateUserStatsJoin(yesterday);
//    }

    @Scheduled(cron = "0 20 12 * * *")
    @Transactional
    public void runStats() {
        LocalDate today = LocalDate.now();
        if (today.equals(LocalDate.of(2025, 4, 10))) { // 오늘 날짜
            LocalDate yesterday = today.minusDays(1);
            userMsService.updateUserStatsJoin(yesterday); // 유저 가입수
            userMsService.updateUserStatsDelete(yesterday); // 유저 탈퇴수
            userMsService.updateUserStatsTotal(yesterday); // 유저 총수
        }
    }


}
