package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.dto.response.post.AdminPostListResponse;
import com.application.foodhubAdmin.service.AdminPostListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/post")
@RequiredArgsConstructor
public class AdminPostListController {

    private final AdminPostListService adminPostListService;

    @GetMapping("/notice")
    public ResponseEntity<List<AdminPostListResponse>> getNoticePosts() {
        List<Post> postList = adminPostListService.findNoticePosts();
        List<AdminPostListResponse> responses = postList.stream()
                .map(AdminPostListResponse::of)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{postId}/delete")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) throws IllegalAccessException {
        adminPostListService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{postId}/restore")
    public ResponseEntity<Void> restorePost(@PathVariable Long postId) throws IllegalAccessException {
        adminPostListService.restorePost(postId);
        return ResponseEntity.ok().build();
    }
}
