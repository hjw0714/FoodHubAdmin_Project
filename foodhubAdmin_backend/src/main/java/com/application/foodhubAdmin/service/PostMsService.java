package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.repository.PostMsRepository;
import com.application.foodhubAdmin.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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


    // 총 게시글 수 통계 저장                // 5 - 12
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
public List<MonthlyTotalPostCntResponse> getMonthlyTotalPostCnt() {
    return statsRepository.getMonthlyTotalPostCnt();
}

// 일별 총 게시글 조회
public List<DailyTotalPostCntResponse> getDailyTotalPostCnt() {
    return statsRepository.getDailyTotalPostCnt();
}


public List<YearlyCategoryPostCntResponse> getYearlyCategoryPostCnt(Integer categoryId) {
    return statsRepository.getYearlyCategoryPostCnt(categoryId);
}

public List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt(Integer  categoryId) {
    return statsRepository.getMonthlyCategoryPostCnt(categoryId);
}

public List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt(Integer categoryId) {
    return  statsRepository.getDailyCategoryPostCnt(categoryId);
}

//    // 연도, 카테고리별 총 게시글 조회
//    public List<YearlyCategoryPostCntResponse> getYearlyCategoryPostCnt(category) {
//        return statsRepository.getYearlyCategoryPostCnt();
//    }
//
//    // 월, 카테고리별 총 게시글 조회
//    public List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt() {
//        return statsRepository.getMonthlyCategoryPostCnt();
//    }
//
//    // 일, 카테고리별 총 게시글 조회
//    public List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt() {
//        return statsRepository.getDailyCategoryPostCnt();
//    }

}
