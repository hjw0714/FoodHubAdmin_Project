package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.dto.response.comments.DailyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.YearlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.repository.CommentsRepository;
import com.application.foodhubAdmin.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final StatsRepository statsRepository;

    public void insertCommentsStatsTotal( LocalDate date) {

        Long totalCount = commentsRepository.countTotalComments();
        Optional<Stats> optionalStats = statsRepository.findByCategoryIdAndStatDate(13, date);

        if (optionalStats.isPresent()) {
            Stats existing = optionalStats.get();
            existing.setStatCnt(totalCount);
            existing.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(existing);
        } else {
            Stats stats = Stats.builder()
                    .categoryId(13)
                    .statDate(date)
                    .statCnt(totalCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            statsRepository.save(stats);
        }
    }

    // 년도별 총 댓글 조회
    public List<YearlyTotalCommentsCntResponse> getYearlyTotalCommentsCnt() {
        return statsRepository.getYearlyTotalCommentsCnt();
    }

    // 월별 총 댓글 조회
    public List<MonthlyTotalCommentsCntResponse> getMonthlyTotalCommentsCnt() {
        return statsRepository.getMonthlyTotalCommentsCnt();
    }

    // 일별 총 댓글 조회
    public List<DailyTotalCommentsCntResponse> getDailyTotalCommentsCnt() {
        return statsRepository.getDailyTotalCommentsCnt();
    }


//    // 연도별 새 댓글
//    public List<YearlyNewCommentsCntResponse> getYearlyNewCommentsCnt() {
//        return commentsRepository.getYearlyNewCommentsCnt();
//    }
//
//    // 월별 새 댓글
//    public List<MonthlyNewCommentsCntResponse> getMonthlyNewCommentsCnt() {
//        return commentsRepository.getMonthlyNewCommentsCnt();
//    }
//
//    // 일별 새 댓글
//    public List<DailyNewCommentsCntResponse> getDailyNewCommentsCnt() {
//        return commentsRepository.getDailyNewCommentsCnt();
//    }

}
