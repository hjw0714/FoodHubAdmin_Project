package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.service.PostMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    public ResponseEntity<List<MonthlyTotalPostCntResponse>> monthlyTotalPost(@RequestParam("startDate") String startDate)  {
        return ResponseEntity.ok(postMsService.getMonthlyTotalPostCnt(startDate));
    }

    // 일별 새 게시글
    @GetMapping("/dailyTotalPost")
    public ResponseEntity<List<DailyTotalPostCntResponse>> dailyNewPost(@RequestParam("startDate") String startDate) throws ParseException {
        return ResponseEntity.ok(postMsService.getDailyTotalPostCnt(startDate));
    }




    // 연도, 카테고리별 총 게시글
    @GetMapping("/yearlyCategoryPost")
    public ResponseEntity<List<YearlyCategoryPostCntResponse>> yearlyCategoryPost(@RequestParam("categoryId") Integer categoryId) {
        return ResponseEntity.ok(postMsService.getYearlyCategoryPostCnt(categoryId));
    }

    // 월, 카테고리별 총 게시글
    @GetMapping("/monthlyCategoryPost")
    public ResponseEntity<List<MonthlyCategoryPostCntResponse>> monthlyCategoryPost(@RequestParam("categoryId") Integer categoryId,
                                                                                    @RequestParam("startDate") String startDate) {
        return ResponseEntity.ok(postMsService.getMonthlyCategoryPostCnt(categoryId, startDate));
    }

    // 일, 카테고리별 총 게시글
    @GetMapping("/dailyCategoryPost")
    public ResponseEntity<List<DailyCategoryPostCntResponse>> dailyCategoryPost(@RequestParam("categoryId") Integer categoryId,
                                                                                @RequestParam("startDate") String startDate) throws ParseException {
        return ResponseEntity.ok(postMsService.getDailyCategoryPostCnt(categoryId, startDate));
    }

    // 대시보드 게시물
    @GetMapping("/totalPost")
    public ResponseEntity<List<MonthlyTotalPostCntResponse>> totalPost()  {
        return ResponseEntity.ok(postMsService.getTotalPostCnt());
    }

    // 신고 게시글 내용
    @GetMapping("/postContent/{id}")
    public ResponseEntity<PostResponse> postContent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postMsService.getPostContent(id));
    }

}
