package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.DailyTotalPostCntResponse;
import com.application.foodhubAdmin.dto.response.MonthlyTotalPostCntResponse;
import com.application.foodhubAdmin.dto.response.YearlyTotalPostCntResponse;
import com.application.foodhubAdmin.service.PostMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class PostMsController {

    private final PostMsService postMsService;

    // 연도별 총 게시글
    @GetMapping("/yearlyTotalPost")
    public ResponseEntity<List<YearlyTotalPostCntResponse>> yearlyTotalPost() {
        return ResponseEntity.ok(postMsService.getYearlyTotalPostCnt());
    }

    // 월별 총 게시글
    @GetMapping("/monthlyTotalPost")
    public ResponseEntity<List<MonthlyTotalPostCntResponse>> monthlyTotalPost() {
        return ResponseEntity.ok(postMsService.getMonthlyTotalPostCnt());
    }

    // 일별 총 게시글
    @GetMapping("/dailyTotalPost")
    public ResponseEntity<List<DailyTotalPostCntResponse>> dailyTotalPost() {
        return ResponseEntity.ok(postMsService.getDailyTotalPostCnt());
    }

}
