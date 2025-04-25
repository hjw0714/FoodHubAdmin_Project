package com.application.foodhubAdmin.dto.request;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.domain.PostStatus;
import com.application.foodhubAdmin.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCreateRequest {

    private Long categoryId;
    private Long subCateId;
    private String title;
    private String userId;
    private String content;
    private String categoryNm;
    private String subCateNm;
    private MultipartFile file;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .nickname(user.getNickname())
                .categoryId(this.categoryId)
                .subCateId(this.subCateId)
                .title(this.title)
                .content(this.content)
                .categoryNm(this.categoryNm)
                .subCateNm(this.subCateNm)
                .viewCnt(0L)
                .status(PostStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


}
