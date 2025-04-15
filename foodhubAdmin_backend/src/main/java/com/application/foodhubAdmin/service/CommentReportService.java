package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.CommentReport;
import com.application.foodhubAdmin.domain.CommentReportStatus;
import com.application.foodhubAdmin.domain.CommentStatus;
import com.application.foodhubAdmin.domain.Comments;
import com.application.foodhubAdmin.dto.response.comments.CommentReportResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyCommentReportResponse;
import com.application.foodhubAdmin.repository.CommentReportRepository;
import com.application.foodhubAdmin.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;
    private final CommentsRepository commentsRepository;

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
        return commentReportRepository.getCommentReportCnt();
    }

}
