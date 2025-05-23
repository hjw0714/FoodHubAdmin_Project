package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VisitorLogMsRepository extends JpaRepository<VisitorLog, Long> {

    // 방문자 수 통계 저장
    @Query("""
    SELECT COUNT(v)
    FROM VisitorLog v
    WHERE FUNCTION('DATE', v.visitTime) = :date
    """)
    Long countVisitorLog(@Param("date") LocalDate date);


}
