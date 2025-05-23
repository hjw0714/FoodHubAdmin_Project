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
@Table(name = "COMMENTS")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID", nullable = false)
    private Long id;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "CONTENT" , nullable = false)
    private String content;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private CommentStatus status;

    public void changeStatus(CommentStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

}
