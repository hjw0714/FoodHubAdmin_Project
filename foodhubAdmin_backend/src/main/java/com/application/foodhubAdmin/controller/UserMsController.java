package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.request.UserLogInRequest;
import com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse;
import com.application.foodhubAdmin.service.UserMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class UserMsController {

    private final UserMsService userMsService;

    // 로그인
    @PostMapping("/logIn")
    public ResponseEntity<?> logIn (@RequestBody UserLogInRequest requestDto) {
        String token = userMsService.logIn(requestDto);
        return ResponseEntity.ok(token);
    }

    // 년도별 신규 가입자 수
    @GetMapping("/yearlyNewUser")
    public ResponseEntity<List<YearlyNewUserCntResponse>> yearlyNewUser() {
        return ResponseEntity.ok(userMsService.getYearlyNewUserCnt());
    }

    // 월별 신규 가입자 수
    @GetMapping("/monthlyNewUser")
    public ResponseEntity<List<MonthlyNewUserCntResponse>> monthlyNewUser() {
        return ResponseEntity.ok(userMsService.getMonthlyNewUserCnt());
    }

    // 일별 신규 가입자 수
    @GetMapping("/dailyNewUser")
    public ResponseEntity<List<DailyNewUserCntResponse>> dailyNewUser() {
        return ResponseEntity.ok(userMsService.getDailyNewUserCnt());
    }


}
