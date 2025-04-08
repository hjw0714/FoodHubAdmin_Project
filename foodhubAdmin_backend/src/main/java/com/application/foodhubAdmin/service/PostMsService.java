package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.dto.response.post.DailyNewPostCntResponse;
import com.application.foodhubAdmin.dto.response.post.MonthlyNewPostCntResponse;
import com.application.foodhubAdmin.dto.response.post.YearlyNewPostCntResponse;
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

}
