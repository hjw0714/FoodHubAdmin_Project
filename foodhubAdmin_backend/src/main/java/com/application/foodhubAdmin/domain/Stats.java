package com.application.foodhubAdmin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "STATS")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statsId;

    @Column(nullable = false)
    private String cate; // "USER"

    @Column(nullable = false)
    private LocalDate term; // 기준 날짜 YYYY-MM-DD

    private Long joinCnt;
    private Long deletedCnt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
