package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.dto.response.*;
import com.application.foodhubAdmin.repository.PostMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMsService {

    private PostMsRepository postMsRepository;

    // 연도별 총 게시글
    public List<YearlyTotalPostCntResponse> getYearlyTotalPostCnt() {
        return postMsRepository.getYearlyTotalPostCnt();
    }

    // 월별 총 게시글
    public List<MonthlyTotalPostCntResponse> getMonthlyTotalPostCnt() {
        return postMsRepository.getMonthlyTotalPostCnt();
    }

    // 일별 총 게시글
    public List<DailyTotalPostCntResponse> getDailyTotalPostCnt() {
        return postMsRepository.getDailyTotalPostCnt();
    }

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

}
