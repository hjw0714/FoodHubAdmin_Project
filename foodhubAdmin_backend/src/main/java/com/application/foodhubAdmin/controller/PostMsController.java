package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.service.PostMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ResponseEntity<List<MonthlyNewPostCntResponse>> monthlyNewPost(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate) {
        return ResponseEntity.ok(postMsService.getMonthlyNewPostCnt(startDate));
    }

    // 일별 새 게시글
    @GetMapping("/dailyNewPost")
    public ResponseEntity<List<DailyNewPostCntResponse>> dailyNewPost(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate) {
        return ResponseEntity.ok(postMsService.getDailyNewPostCnt(startDate));
    }

//    // 연도, 카테고리별 새 게시글
//    @GetMapping("/yearlyCategoryPost")
//    public ResponseEntity<List<YearlyCategoryPostCntResponse>> yearlyCategoryPost() {
//        return ResponseEntity.ok(postMsService.getYearlyCategoryPostCnt());
//    }
//
//    // 월, 카테고리별 새 게시글
//    @GetMapping("/monthlyCategoryPost")
//    public ResponseEntity<List<MonthlyCategoryPostCntResponse>> monthlyCategoryPost() {
//        return ResponseEntity.ok(postMsService.getMonthlyCategoryPostCnt());
//    }
//
//    // 일, 카테고리별 새 게시글
//    @GetMapping("/dailyCategoryPost")
//    public ResponseEntity<List<DailyCategoryPostCntResponse>> dailyCategoryPost() {
//        return ResponseEntity.ok(postMsService.getDailyCategoryPostCnt());
//    }

}
