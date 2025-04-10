package com.application.foodhubAdmin.dto.response.post;

import com.application.foodhubAdmin.domain.PostReport;
import com.application.foodhubAdmin.domain.PostReportStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostReportResponse {

    private Long id;
    private Long postId;
    private String postTitle;
    private String userId;
    private String content;
    private PostReportStatus postReportStatus;
    private LocalDateTime createdAt;
    private String postStatus;

    public static PostReportResponse of (PostReport postReport) {
        return PostReportResponse.builder()
                .id(postReport.getId())
                .postId(postReport.getPost().getId())
                .postTitle(postReport.getPost().getTitle())
                .userId(postReport.getUserId())
                .content(postReport.getContent())
                .postReportStatus(postReport.getStatus())
                .createdAt(postReport.getCreatedAt())
                .postStatus(postReport.getPost().getStatus().name()) // postReport에서 postId를 통해 Post의 상태를 가지고 와서 문자열로 변환
                .build();
    }

}
