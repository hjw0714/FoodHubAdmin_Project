package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.VisitorLog;
import com.application.foodhubAdmin.dto.response.visitorLog.DailyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.YearlyVisitorLogResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitorLogMsRepository extends JpaRepository<VisitorLog, Long> {

    // 연도별 방문자 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.visitorLog.YearlyVisitorLogResponse(YEAR(v.visitTime), count(v))
            FROM VisitorLog v
            GROUP BY YEAR(v.visitTime)
            ORDER BY YEAR(v.visitTime)
            """)
    List<YearlyVisitorLogResponse> getYearlyVisitorLog();

    // 월별 방문자 수
    @Query("""
           SELECT new com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse(FUNCTION('DATE_FORMAT', v.visitTime, '%Y-%m'), COUNT(v))
            FROM VisitorLog v
            WHERE CONCAT(FUNCTION('YEAR', v.visitTime), '-', FUNCTION('MONTH', v.visitTime)) >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', v.visitTime, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', v.visitTime, '%Y-%m')
           """)
    List<MonthlyVisitorLogResponse> getMonthlyVisitorLog(@Param("startDate") String startDate);

    // 일별 방문자 수
    @Query("""
           SELECT new com.application.foodhubAdmin.dto.response.visitorLog.DailyVisitorLogResponse(
                  FUNCTION('DATE', v.visitTime) AS day, COUNT(v) AS visitorCnt)
            FROM VisitorLog v
            WHERE FUNCTION('DATE', v.visitTime) >= :parsedStartDate
            GROUP BY FUNCTION('DATE', v.visitTime)
            ORDER BY FUNCTION('DATE', v.visitTime) ASC
           """)
    List<DailyVisitorLogResponse> getDailyVisitorLog(@Param("parsedStartDate") Date parsedStartDate);

    // 대시보드; 방문자 수
    @Query("""
           SELECT new com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse(FUNCTION('DATE_FORMAT', v.visitTime, '%Y-%m'), COUNT(v))
            FROM VisitorLog v
            GROUP BY FUNCTION('DATE_FORMAT', v.visitTime, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', v.visitTime, '%Y-%m')
           """)
    List<MonthlyVisitorLogResponse> getVisitorLog();

}
