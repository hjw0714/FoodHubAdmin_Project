package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.service.PostMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PostMsController {

    private final PostMsService postMsService;

    // 연도별 새 게시글
    @GetMapping("/yearlyTotalPost")
    public ResponseEntity<List<YearlyTotalPostCntResponse>> yearlyTotalPost() {
        return ResponseEntity.ok(postMsService.getYearlyTotalPostCnt());
    }

    // 월별 새 게시글
    @GetMapping("/monthlyTotalPost")
    public ResponseEntity<List<MonthlyTotalPostCntResponse>> monthlyTotalPost()  {
        return ResponseEntity.ok(postMsService.getMonthlyTotalPostCnt());
    }

    // 일별 새 게시글
    @GetMapping("/dailyTotalPost")
    public ResponseEntity<List<DailyTotalPostCntResponse>> dailyNewPost() {
        return ResponseEntity.ok(postMsService.getDailyTotalPostCnt());
    }

//    // 연도, 카테고리별 총 게시글
//    @GetMapping("/yearlyCategoryPost")
//    public ResponseEntity<List<YearlyCategoryPostCntResponse>> yearlyCategoryPost() {
//        return ResponseEntity.ok(postMsService.getYearlyCategoryPostCnt());
//    }
//
//    // 월, 카테고리별 총 게시글
//    @GetMapping("/monthlyCategoryPost")
//    public ResponseEntity<List<MonthlyCategoryPostCntResponse>> monthlyCategoryPost() {
//        return ResponseEntity.ok(postMsService.getMonthlyCategoryPostCnt());
//    }
//
//    // 일, 카테고리별 총 게시글
//    @GetMapping("/dailyCategoryPost")
//    public ResponseEntity<List<DailyCategoryPostCntResponse>> dailyCategoryPost() {
//        return ResponseEntity.ok(postMsService.getDailyCategoryPostCnt());
//    }

}
