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
    private final PostMsService postMsService;
    private final CommentsService commentsService;
    private final VisitorLogMsService visitorLogMsService;

//    @Scheduled(cron = "0 0 0 * * *")
//    @Transactional
//    public void runStats() {
//        LocalDate yesterday = LocalDate.now().minusDays(1);
//            userMsService.insertUserStatsJoin(yesterday); // 유저 가입수
//            userMsService.insertUserStatsDelete(yesterday); // 유저 탈퇴수
//            userMsService.insertUserStatsTotal(yesterday); // 유저 총수
//            postMsService.insertPostStatsTotal(yesterday); // 게시글 총 작성수
//            postMsService.insertPostCategoryStatsTotal(yesterday); // 게시글 카테고리별 총 작성수
//    }

    @Scheduled(cron = "0 50 9 * * *")
    @Transactional
    public void runStats() {
        LocalDate today = LocalDate.now();
        if (today.equals(LocalDate.of(2025, 4, 18))) { // 오늘 날짜
            LocalDate yesterday = today.minusDays(1);
            userMsService.insertUserStatsJoin(yesterday); // 유저 가입수
            userMsService.insertUserStatsDelete(yesterday); // 유저 탈퇴수
            userMsService.insertUserStatsTotal(yesterday); // 유저 총수
            postMsService.insertPostStatsTotal(yesterday); // 게시글 총 작성수
            postMsService.insertPostCategoryStatsTotal(yesterday); // 게시글 카테고리별 총 작성수
            commentsService.insertCommentsStatsTotal(yesterday); // 댓글 총 작성수
            visitorLogMsService.insertVisitorLogTotal(yesterday);

        }
    }


}
