package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.dto.response.visitorLog.DailyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.YearlyVisitorLogResponse;
import com.application.foodhubAdmin.repository.VisitorLogMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorLogMsService {

    private final VisitorLogMsRepository visitorLogMsRepository;

    // 연도별 방문자 수
    public List<YearlyVisitorLogResponse> getYearlyVisitorLog() {
        return visitorLogMsRepository.getYearlyVisitorLog();
    }

    // 월별 방문자 수
    public List<MonthlyVisitorLogResponse> getMonthlyVisitorLog(String startDate) {
        String year = startDate.substring(0, 4);
        String month = startDate.substring(5, 7);
        if(month.startsWith("0")) {
            month = month.substring(1);
        }
        startDate = year + "-" + month;
        return visitorLogMsRepository.getMonthlyVisitorLog(startDate);
    }

    // 일별 방문자 수
    public List<DailyVisitorLogResponse> getDailyVisitorLog(String startDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = dateFormat.parse(startDate);
        System.out.println("service1: " + visitorLogMsRepository.getDailyVisitorLog(parsedStartDate));
        return visitorLogMsRepository.getDailyVisitorLog(parsedStartDate);
    }

    // 대시보드; 방문자 수
    public List<MonthlyVisitorLogResponse> getVisitorLog() {
        return visitorLogMsRepository.getVisitorLog();
    }


}
