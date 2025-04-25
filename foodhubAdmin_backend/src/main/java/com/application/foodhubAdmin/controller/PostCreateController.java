package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.request.PostCreateRequest;
import com.application.foodhubAdmin.service.PostCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/post")
public class PostCreateController {

    private final PostCreateService postCreateService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@ModelAttribute PostCreateRequest requestDto) {
        try {
            postCreateService.createPost(requestDto);
            return ResponseEntity.ok("게시글이 등록되었습니다.");
        } catch(MultipartException e) {
            return ResponseEntity.badRequest().body("파일 업로드에서 오류가 발생했습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류: " + e.getMessage());
        }
    }
}
