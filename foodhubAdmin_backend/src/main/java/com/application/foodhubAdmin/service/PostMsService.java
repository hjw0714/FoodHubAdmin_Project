package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.repository.PostMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMsService {

    private final PostMsRepository postMsRepository;

    // 연도별 새 게시글
    public List<YearlyNewPostCntResponse> getYearlyNewPostCnt() {
        return postMsRepository.getYearlyNewPostCnt();
    }

    // 월별 새 게시글
    public List<MonthlyNewPostCntResponse> getMonthlyNewPostCnt(LocalDate startDate) {
        if (startDate == null) {
            startDate = LocalDate.now().minusYears(1); // 디폴트: 최근 1년
        }
        return postMsRepository.getMonthlyNewPostCnt(startDate);
    }

    // 일별 새 게시글
    public List<DailyNewPostCntResponse> getDailyNewPostCnt(LocalDate startDate) {
        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1); // 디폴트: 최근 1달
        }
        return postMsRepository.getDailyNewPostCnt(startDate);
    }

//    // 연도, 카테고리별 새 게시글
//    public List<YearlyCategoryPostCntResponse> getYearlyCategoryPostCnt() {
//        return postMsRepository.getYearlyCategoryPostCnt();
//    }
//
//    // 월, 카테고리별 새 게시글
//    public List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt() {
//        return postMsRepository.getMonthlyCategoryPostCnt();
//    }
//
//    // 일, 카테고리별 새 게시글
//    public List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt() {
//        return postMsRepository.getDailyCategoryPostCnt();
//    }

}
