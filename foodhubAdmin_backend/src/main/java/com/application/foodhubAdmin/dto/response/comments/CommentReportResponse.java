package com.application.foodhubAdmin.dto.response.comments;

import com.application.foodhubAdmin.domain.CommentReport;
import com.application.foodhubAdmin.domain.CommentReportStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentReportResponse {

    private Long id;
    private Long commentId;
    private String commentContent;
    private String userId;
    private String content;
    private CommentReportStatus commentReportStatus;
    private LocalDateTime createdAt;
    private String commentStatus;

    public static CommentReportResponse of(CommentReport commentReport) {
        return CommentReportResponse.builder()
                .id(commentReport.getId())
                .commentId(commentReport.getComments().getId())
                .commentContent(commentReport.getComments().getContent())
                .userId(commentReport.getUserId())
                .content(commentReport.getContent())
                .commentReportStatus(commentReport.getStatus())
                .createdAt(commentReport.getCreatedAt())
                .commentStatus(commentReport.getComments().getStatus().name())
                .build();
    }

}
