package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.comments.DailyNewCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyNewCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.YearlyNewCommentsCntResponse;
import com.application.foodhubAdmin.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    // 연도별 새 댓글
    @GetMapping("/yearlyNewComments")
    public ResponseEntity<List<YearlyNewCommentsCntResponse>> yearlyNewComments() {
        return ResponseEntity.ok(commentsService.getYearlyNewCommentsCnt());
    }

    // 월별 새 댓글
    @GetMapping("/monthlyNewComments")
    public ResponseEntity<List<MonthlyNewCommentsCntResponse>> monthlyNewComments() {
        return ResponseEntity.ok(commentsService.getMonthlyNewCommentsCnt());
    }

    // 일별 새 댓글
    @GetMapping("/dailyNewComments")
    public ResponseEntity<List<DailyNewCommentsCntResponse>> dailyNewComments() {
        return ResponseEntity.ok(commentsService.getDailyNewCommentsCnt());
    }

}
