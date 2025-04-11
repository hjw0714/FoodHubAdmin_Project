package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.comments.CommentReportResponse;
import com.application.foodhubAdmin.service.CommentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/comment-report")
public class CommentReportController {

    private final CommentReportService commentReportService;

    @GetMapping
    public List<CommentReportResponse> getAllCommentReports() {
        return commentReportService.getAllCommentReports();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeReportStatus(@PathVariable Long id, @RequestBody Map<String , String> reportRequest) {
        String status = reportRequest.get("status");
        commentReportService.changeReportStatus(id , status);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentReportService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

}
