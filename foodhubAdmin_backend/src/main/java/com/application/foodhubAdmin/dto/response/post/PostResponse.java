package com.application.foodhubAdmin.dto.response.post;

import com.application.foodhubAdmin.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String nickname;
    private String title;
    private String content;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

}
