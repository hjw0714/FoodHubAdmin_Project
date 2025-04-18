package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.dto.response.visitorLog.DailyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.YearlyVisitorLogResponse;
import com.application.foodhubAdmin.repository.StatsRepository;
import com.application.foodhubAdmin.repository.VisitorLogMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorLogMsService {

    private final VisitorLogMsRepository visitorLogMsRepository;
    private final StatsRepository statsRepository;

    // 방문자 수 통계 저장
    public void insertVisitorLogTotal(LocalDate date) {
        Long totalCount = visitorLogMsRepository.countVisitorLog(date);

        Optional<Stats> optionalStats = statsRepository.findByCategoryIdAndStatDate(14, date);

        if (optionalStats.isPresent()) {
            Stats existing = optionalStats.get();
            existing.setStatCnt(totalCount);
            existing.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(existing);
        } else {
            Stats stats = Stats.builder()
                    .categoryId(14) // 방문자수
                    .statDate(date)
                    .statCnt(totalCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            statsRepository.save(stats);
        }
    }

    // 연도별 방문자 수
    public List<YearlyVisitorLogResponse> getYearlyVisitorLog() {
        return statsRepository.getYearlyVisitorLog();
    }

    // 월별 방문자 수
    public List<MonthlyVisitorLogResponse> getMonthlyVisitorLog(String startDate) {
        String year = startDate.substring(0, 4);
        String month = startDate.substring(5, 7);
        if(month.startsWith("0")) {
            month = month.substring(1);
        }
        startDate = year + "-" + month;
        return statsRepository.getMonthlyVisitorLog(startDate);
    }

    // 일별 방문자 수
    public List<DailyVisitorLogResponse> getDailyVisitorLog(String startDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = dateFormat.parse(startDate);
        return statsRepository.getDailyVisitorLog(parsedStartDate);
    }

    // 대시보드; 방문자 수
    public List<MonthlyVisitorLogResponse> getVisitorLog() {
        return statsRepository.getVisitorLog();
    }


}
