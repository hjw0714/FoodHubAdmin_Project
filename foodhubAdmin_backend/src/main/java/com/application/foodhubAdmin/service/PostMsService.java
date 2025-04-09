package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.repository.PostMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<MonthlyNewPostCntResponse> getMonthlyNewPostCnt() {
        return postMsRepository.getMonthlyNewPostCnt();
    }

    // 일별 새 게시글
    public List<DailyNewPostCntResponse> getDailyNewPostCnt() {
        return postMsRepository.getDailyNewPostCnt();
    }

    // 연도, 카테고리별 새 게시글
    public List<YearlyCategoryPostCntResponse> getYearlyCategoryPostCnt() {
        return postMsRepository.getYearlyCategoryPostCnt();
    }

    // 월, 카테고리별 새 게시글
    public List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt() {
        return postMsRepository.getMonthlyCategoryPostCnt();
    }

    // 일, 카테고리별 새 게시글
    public List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt() {
        return postMsRepository.getDailyCategoryPostCnt();
    }

}
