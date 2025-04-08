package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.dto.response.DailyTotalPostCntResponse;
import com.application.foodhubAdmin.dto.response.MonthlyTotalPostCntResponse;
import com.application.foodhubAdmin.dto.response.YearlyTotalPostCntResponse;
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
        System.out.println(postMsRepository.getYearlyTotalPostCnt());
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

}
