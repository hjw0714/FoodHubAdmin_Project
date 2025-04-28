package com.application.foodhubAdmin.dto.response.post;

import com.application.foodhubAdmin.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminPostListResponse {

    private Long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private String status;

    public static AdminPostListResponse of (Post post) {
        return AdminPostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getNickname())
                .createdAt(post.getCreatedAt())
                .status(post.getStatus().name())
                .build();
    }

}
