package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.service.PostMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PostMsController {

    private final PostMsService postMsService;

    // 연도별 새 게시글
    @GetMapping("/yearlyNewPost")
    public ResponseEntity<List<YearlyNewPostCntResponse>> yearlyNewPost() {
        return ResponseEntity.ok(postMsService.getYearlyNewPostCnt());
    }

    // 월별 새 게시글
    @GetMapping("/monthlyNewPost")
    public ResponseEntity<List<MonthlyNewPostCntResponse>> monthlyNewPost() {
        return ResponseEntity.ok(postMsService.getMonthlyNewPostCnt());
    }

    // 일별 새 게시글
    @GetMapping("/dailyNewPost")
    public ResponseEntity<List<DailyNewPostCntResponse>> dailyNewPost() {
        return ResponseEntity.ok(postMsService.getDailyNewPostCnt());
    }

    // 연도, 카테고리별 새 게시글
    @GetMapping("/yearlyCategoryPost")
    public ResponseEntity<List<YearlyCategoryPostCntResponse>> yearlyCategoryPost() {
        return ResponseEntity.ok(postMsService.getYearlyCategoryPostCnt());
    }

    // 월, 카테고리별 새 게시글
    @GetMapping("/monthlyCategoryPost")
    public ResponseEntity<List<MonthlyCategoryPostCntResponse>> monthlyCategoryPost() {
        return ResponseEntity.ok(postMsService.getMonthlyCategoryPostCnt());
    }

    // 일, 카테고리별 새 게시글
    @GetMapping("/dailyCategoryPost")
    public ResponseEntity<List<DailyCategoryPostCntResponse>> dailyCategoryPost() {
        return ResponseEntity.ok(postMsService.getDailyCategoryPostCnt());
    }

}
