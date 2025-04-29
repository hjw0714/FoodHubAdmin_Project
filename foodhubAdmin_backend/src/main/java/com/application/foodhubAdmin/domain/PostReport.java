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
@Table(name = "POST_REPORT")
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @Column(name = "USER_ID", nullable = false)
    private String userId; // User 엔티티가 있으면 ManyToOne으로 변경

    @Column(name = "CONTENT", nullable = false, length = 2000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private PostReportStatus status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public void changeStatus(PostReportStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
