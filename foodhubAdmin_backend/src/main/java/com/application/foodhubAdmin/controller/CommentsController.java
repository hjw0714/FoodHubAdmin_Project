package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.comments.DailyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.YearlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    // 연도별 새 댓글
    @GetMapping("/yearlyTotalComments")
    public ResponseEntity<List<YearlyTotalCommentsCntResponse>> yearlyTotalComments() {
        return ResponseEntity.ok(commentsService.getYearlyTotalCommentsCnt());
    }
    // 월별 새 댓글
    @GetMapping("/monthlyTotalComments")
    public ResponseEntity<List<MonthlyTotalCommentsCntResponse>> monthlyTotalComments() {
        return ResponseEntity.ok(commentsService.getMonthlyTotalCommentsCnt());
    }

    // 일별 새 댓글
    @GetMapping("/dailyTotalComments")
    public ResponseEntity<List<DailyTotalCommentsCntResponse>> dailyTotalComments() {
        return ResponseEntity.ok(commentsService.getDailyTotalCommentsCnt());
    }

}
