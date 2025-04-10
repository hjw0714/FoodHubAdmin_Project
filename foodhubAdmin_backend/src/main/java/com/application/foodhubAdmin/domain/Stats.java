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
@Table(
        name = "STATS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"STAT_DATE", "CATEGORY_ID"})
        }
)
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATS_ID")
    private Long statsId;

    @Column(name = "CATEGORY_ID", nullable = false)
    private Integer categoryId; // 1) USER_JOIN, 2) USER_DELETE ...

    @Column(name = "STAT_DATE", nullable = false)
    private LocalDate statDate; // YYYY-MM-DD

    @Column(name = "STAT_CNT")
    private Long statCnt;

    @Column(name = "CREATED_AT", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
