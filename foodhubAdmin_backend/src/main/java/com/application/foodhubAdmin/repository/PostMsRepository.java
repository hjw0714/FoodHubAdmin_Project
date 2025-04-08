package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.dto.response.DailyTotalPostCntResponse;
import com.application.foodhubAdmin.dto.response.MonthlyTotalPostCntResponse;
import com.application.foodhubAdmin.dto.response.YearlyTotalPostCntResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMsRepository extends JpaRepository<Post, Long> {

    // 연도별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.YearlyTotalPostCntResponse(
                YEAR(p.createdAt) as year,
                COUNT(p) as yearlyPostCnt,
                SUM(COUNT(p)) OVER (ORDER BY YEAR(p.createdAt) ASC) AS postCnt)
            FROM Post p
            GROUP BY YEAR(p.createdAt)
            ORDER BY YEAR(p.createdAt) ASC
            """)
    List<YearlyTotalPostCntResponse> getYearlyTotalPostCnt();

    // 월별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.MonthlyTotalPostCntResponse(
                CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)),
                COUNT(p) as monthlyPostCnt,
                SUM(COUNT(p)) OVER (ORDER BY FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)) ASC) AS postCnt)
            FROM Post p
            GROUP BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)
            ORDER BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt) ASC
            """)
    List<MonthlyTotalPostCntResponse> getMonthlyTotalPostCnt();

    // 일별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.DailyTotalPostCntResponse(
                FUNCTION('DATE', p.createdAt) AS day,
                COUNT(p) as dailyPostCnt,
                SUM(COUNT(p)) OVER (ORDER BY FUNCTION('DATE', p.createdAt) ASC) AS postCnt)
            FROM Post p
            GROUP BY FUNCTION('DATE', p.createdAt)
            ORDER BY FUNCTION('DATE', p.createdAt) ASC
            """)
    List<DailyTotalPostCntResponse> getDailyTotalPostCnt();

}
