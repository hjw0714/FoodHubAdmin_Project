package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.*;
import com.application.foodhubAdmin.dto.response.comments.CommentReportResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyCommentReportResponse;
import com.application.foodhubAdmin.repository.CommentReportRepository;
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
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;
    private final CommentsRepository commentsRepository;
    private final StatsRepository statsRepository;

    // 댓글 신고 통계 저장
    @Transactional
    public void insertCommentReportTotal( LocalDate date) {

        Long totalCount = commentReportRepository.countCommentReport();
        Optional<Stats> optionalStats = statsRepository.findByCategoryIdAndStatDate(16, date);

        if (optionalStats.isPresent()) {
            Stats existing = optionalStats.get();
            existing.setStatCnt(totalCount);
            existing.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(existing);
        } else {
            Stats stats = Stats.builder()
                    .categoryId(16)
                    .statDate(date)
                    .statCnt(totalCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            statsRepository.save(stats);
        }
    }


    public List<CommentReportResponse> getAllCommentReports() {
        List<CommentReport> reports = commentReportRepository.findAll();
        return reports.stream()
                .map(CommentReportResponse::of)
                .toList();
    }

    @Transactional
    public void changeReportStatus(Long id , String status) {
        CommentReport report = commentReportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 신고가 존재하지 않습니다."));
        CommentReportStatus newStatus = CommentReportStatus.valueOf(status); // 문자열을 enum으로 변경
        report.changeStatus(newStatus);

    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment Not Found"));
        comments.changeStatus(CommentStatus.DELETED);
    }

    @Transactional
    public void restoreComment(Long commentId) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment Not Found"));
        comments.changeStatus(CommentStatus.VISIBLE);
    }

    public List<MonthlyCommentReportResponse> getCommentReportCnt() {
        System.out.println("commentReportCnt: " + statsRepository.getCommentReportCnt());
        return statsRepository.getCommentReportCnt();
    }

}
