package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.YearlyTotalPostCntResponse;
import com.application.foodhubAdmin.service.PostMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class PostMsController {

    private final PostMsService postMsService;

    @GetMapping("/yearlyTotalPost")
    public ResponseEntity<List<YearlyTotalPostCntResponse>> yearlyTotalPost() {
        return ResponseEntity.ok(postMsService.getYearlyTotalPostCnt());
    }

}
