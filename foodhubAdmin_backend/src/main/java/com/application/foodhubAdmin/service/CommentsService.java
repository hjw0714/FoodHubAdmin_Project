//package com.application.foodhubAdmin.service;
//
//import com.application.foodhubAdmin.dto.response.comments.DailyNewCommentsCntResponse;
//import com.application.foodhubAdmin.dto.response.comments.MonthlyNewCommentsCntResponse;
//import com.application.foodhubAdmin.dto.response.comments.YearlyNewCommentsCntResponse;
//import com.application.foodhubAdmin.repository.CommentsRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class CommentsService {
//
//    private final CommentsRepository commentsRepository;
//
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
//
//}
