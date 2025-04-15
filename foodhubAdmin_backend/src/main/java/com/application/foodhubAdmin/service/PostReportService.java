package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.domain.PostReport;
import com.application.foodhubAdmin.domain.PostReportStatus;
import com.application.foodhubAdmin.domain.PostStatus;
import com.application.foodhubAdmin.dto.response.post.MonthlyPostReportResponse;
import com.application.foodhubAdmin.dto.response.post.PostReportResponse;
import com.application.foodhubAdmin.repository.PostMsRepository;
import com.application.foodhubAdmin.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostMsRepository postRepository;

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
        return postReportRepository.getPostReportCnt();
    }

}
