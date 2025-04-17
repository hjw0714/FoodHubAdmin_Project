package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorLogMsRepository extends JpaRepository<VisitorLog, Long> {

    // 방문자 수 통계 저장
    @Query("""
           SELECT COUNT(v)
           FROM  VisitorLog v
           GROUP BY FUNCTION('DATE', v.visitTime)
           """)
    Long countVisitorLogOn();

}
