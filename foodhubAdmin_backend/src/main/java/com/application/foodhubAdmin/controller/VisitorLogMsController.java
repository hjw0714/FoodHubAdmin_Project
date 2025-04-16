package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.response.visitorLog.DailyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.YearlyVisitorLogResponse;
import com.application.foodhubAdmin.service.VisitorLogMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/visitor")
@RequiredArgsConstructor
public class VisitorLogMsController {

    private final VisitorLogMsService visitorLogMsService;

    // 연도별 방문자 수
    @GetMapping("/yearlyVisitorLog")
    public ResponseEntity<List<YearlyVisitorLogResponse>> yearlyVisitorLog() {
        return ResponseEntity.ok(visitorLogMsService.getYearlyVisitorLog());
    }

    // 월별 방문자 수
    @GetMapping("/monthlyVisitorLog")
    public ResponseEntity<List<MonthlyVisitorLogResponse>> monthlyVisitorLog(@RequestParam("startDate") String startDate) {
        return ResponseEntity.ok(visitorLogMsService.getMonthlyVisitorLog(startDate));
    }

    // 일별 방문자 수
    @GetMapping("/dailyVisitorLog")
    public ResponseEntity<List<DailyVisitorLogResponse>> dailyVisitorLog(@RequestParam("startDate") String startDate) throws ParseException {
        return ResponseEntity.ok(visitorLogMsService.getDailyVisitorLog(startDate));
    }

    // 대시보드; 방문자 수
    @GetMapping("/chart")
    public ResponseEntity<List<MonthlyVisitorLogResponse>> visitorLog() {
        return ResponseEntity.ok(visitorLogMsService.getVisitorLog());
    }

}
