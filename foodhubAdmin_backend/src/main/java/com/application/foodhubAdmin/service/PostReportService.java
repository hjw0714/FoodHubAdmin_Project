package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.*;
import com.application.foodhubAdmin.dto.response.post.MonthlyPostReportResponse;
import com.application.foodhubAdmin.dto.response.post.PostReportResponse;
import com.application.foodhubAdmin.repository.PostMsRepository;
import com.application.foodhubAdmin.repository.PostReportRepository;
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
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostMsRepository postRepository;
    private final StatsRepository statsRepository;

    // 게시글 신고 통계 저장
    @Transactional
    public void insertPostReportTotal( LocalDate date) {

        Long totalCount = postReportRepository.countPostReport();
        Optional<Stats> optionalStats = statsRepository.findByCategoryIdAndStatDate(15, date);

        if (optionalStats.isPresent()) {
            Stats existing = optionalStats.get();
            existing.setStatCnt(totalCount);
            existing.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(existing);
        } else {
            Stats stats = Stats.builder()
                    .categoryId(15)
                    .statDate(date)
                    .statCnt(totalCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            statsRepository.save(stats);
        }
    }

    public List<PostReportResponse> getAllPostReports() {
        List<PostReport> reports = postReportRepository.findAll();
        return reports.stream()
                .map(PostReportResponse::of)
                .toList();
    }

    @Transactional
    public void changeReportStatus(Long id , String status) {
        PostReport report = postReportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 신고가 존재하지 않습니다."));
        PostReportStatus newStatus = PostReportStatus.valueOf(status); // 문자열을 enum으로 변경
        report.changeStatus(newStatus);

    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post Not Found"));
        post.changeStatus(PostStatus.DELETED);
    }

    @Transactional
    public void restorePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("PostNot Found"));
        post.changeStatus(PostStatus.ACTIVE);
    }

    public List<MonthlyPostReportResponse> getPostReportCnt() {
        return statsRepository.getPostReportCnt();
    }

}
