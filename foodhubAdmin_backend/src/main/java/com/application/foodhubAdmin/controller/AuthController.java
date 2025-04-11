package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.request.UserLogInRequest;
import com.application.foodhubAdmin.service.UserMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserMsService userMsService;

    // 로그인
    @PostMapping("/logIn")
    public ResponseEntity<?> logIn (@RequestBody UserLogInRequest requestDto) {
        String token = userMsService.logIn(requestDto);
        return ResponseEntity.ok(token);
    }

}
