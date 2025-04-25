package com.application.foodhubAdmin.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "CATEGORY_ID", nullable = false)
    private Long categoryId;

    @Column(name = "SUB_CATE_ID", nullable = false)
    private Long subCateId;

    @Column(name = "CATEGORY_NM", nullable = false)
    private String categoryNm;

    @Column(name = "SUB_CATE_NM", nullable = false)
    private String subCateNm;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "VIEW_CNT", nullable = false)
    private Long viewCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private PostStatus status;


    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void changeStatus(PostStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

}
