package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.repository.PostMsRepository;
import com.application.foodhubAdmin.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMsService {

    private final PostMsRepository postMsRepository;
    private final StatsRepository statsRepository;

    // 총 게시글 수 통계 저장
    public void insertPostStatsTotal( LocalDate date) {

        Long totalCount = postMsRepository.countTotalPosts(); // active만 총4개
        Optional<Stats> optionalStats = statsRepository.findByCategoryIdAndStatDate(4, date);

        if (optionalStats.isPresent()) {
            Stats existing = optionalStats.get();
            existing.setStatCnt(totalCount);
            existing.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(existing);
        } else {
            Stats stats = Stats.builder()
                    .categoryId(4)
                    .statDate(date)
                    .statCnt(totalCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            statsRepository.save(stats);
        }
    }


    // 카테고리별 게시글 수 통계 저장                // 5 - 12
    public void insertPostCategoryStatsTotal( LocalDate date) {

        List<Stats> statsList = new ArrayList<>();
        for (int categoryId = 5; categoryId <= 12; categoryId++){
            int findPostCategoryId = categoryId - 5;
            Long totalCount = postMsRepository.countTotalPostsByPostCategoryId(findPostCategoryId); // active만 총4개

            Stats stats = Stats.builder()
                    .categoryId(categoryId)
                    .statDate(date)
                    .statCnt(totalCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            statsList.add(stats);

        }



        statsRepository.saveAll(statsList);
        // statsRepository.save(stats);
    }



// 연도별 총 게시글 조회
public List<YearlyTotalPostCntResponse> getYearlyTotalPostCnt() {
    return statsRepository.getYearlyTotalPostCnt();
}

// 월별 총 게시글 조회
public List<MonthlyTotalPostCntResponse> getMonthlyTotalPostCnt(String startDate) {
    String year = startDate.substring(0, 4);
    String month = startDate.substring(5, 7);
    if(month.startsWith("0")) {
        month = month.substring(1);
    }
    startDate = year + "-" + month;
    return statsRepository.getMonthlyTotalPostCnt(startDate);
}

// 일별 총 게시글 조회
public List<DailyTotalPostCntResponse> getDailyTotalPostCnt(String startDate) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date parsedStartDate = dateFormat.parse(startDate); // String → Date 변환
    return statsRepository.getDailyTotalPostCnt(parsedStartDate);
}

// 연도, 카테고리별 총 게시글 조회
public List<YearlyCategoryPostCntResponse> getYearlyCategoryPostCnt(Integer categoryId) {
    return statsRepository.getYearlyCategoryPostCnt(categoryId);
}

// 월, 카테고리별 총 게시글 조회
public List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt(Integer  categoryId, String startDate) {
    String year = startDate.substring(0, 4);
    String month = startDate.substring(5, 7);
    if(month.startsWith("0")) {
        month = month.substring(1);
    }
    startDate = year + "-" + month;
    return statsRepository.getMonthlyCategoryPostCnt(categoryId, startDate);
}

// 일, 카테고리별 총 게시글 조회
public List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt(Integer categoryId, String startDate) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date parsedStartDate = dateFormat.parse(startDate); // String → Date 변환
    return  statsRepository.getDailyCategoryPostCnt(categoryId, parsedStartDate);
}

// 대시보드 게시물
public List<MonthlyTotalPostCntResponse> getTotalPostCnt() {
    return statsRepository.getTotalPostCnt();
}

}
