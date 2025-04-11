package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.post.PostReportResponse;
import com.application.foodhubAdmin.service.PostReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/post-report")
public class PostReportController {

    private final PostReportService postReportService;

    @GetMapping
    public List<PostReportResponse> getAllPostReports() {
        return postReportService.getAllPostReports();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeReportStatus(@PathVariable Long id, @RequestBody Map<String , String> reportRequest) {
        String status = reportRequest.get("status");
        postReportService.changeReportStatus(id , status);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{postId}/delete")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postReportService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{postId}/restore")
    public ResponseEntity<Void> restorePost(@PathVariable Long postId) {
        postReportService.restorePost(postId);
        return ResponseEntity.ok().build();
    }
}
