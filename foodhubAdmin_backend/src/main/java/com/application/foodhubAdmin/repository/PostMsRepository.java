package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.dto.response.*;
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
                SUM(COUNT(p)) OVER (ORDER BY FUNCTION('DATE', p.createdAt) ASC) AS postCnt)
            FROM Post p
            GROUP BY FUNCTION('DATE', p.createdAt)
            ORDER BY FUNCTION('DATE', p.createdAt) ASC
            """)
    List<DailyTotalPostCntResponse> getDailyTotalPostCnt();

    // 연도별 새 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.YearlyNewPostCntResponse(
                YEAR(p.createdAt) as year,
                COUNT(p) as postCnt)
            FROM Post p
            GROUP BY YEAR(p.createdAt)
            ORDER BY YEAR(p.createdAt) ASC
            """)
    List<YearlyNewPostCntResponse> getYearlyNewPostCnt();

    // 월별 새 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.MonthlyNewPostCntResponse(
                CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)),
                COUNT(p) as postCnt)
            FROM Post p
            GROUP BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)
            ORDER BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt) ASC
            """)
    List<MonthlyNewPostCntResponse> getMonthlyNewPostCnt();

    // 일별 새 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.DailyNewPostCntResponse(
                FUNCTION('DATE', p.createdAt) AS day,
                COUNT(p) as postCnt)
            FROM Post p
            GROUP BY FUNCTION('DATE', p.createdAt)
            ORDER BY FUNCTION('DATE', p.createdAt) ASC
            """)
    List<DailyNewPostCntResponse> getDailyNewPostCnt();

    // 연도, 카테고리별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.YearlyCategoryPostCnt(
                YEAR(p.createdAt) as year,
                SUM(COUNT(p)) OVER (ORDER BY YEAR(p.createdAt) ASC) AS postCnt)
            FROM Post p
            GROUP BY YEAR(p.createdAt) AND p.categoryId
            ORDER BY YEAR(p.createdAt) ASC
            """)
    List<YearlyCategoryPostCnt> getYearlyCategoryPostCnt();

    // 월, 카테고리별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.MonthlyCategoryPostCnt(
                CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)),
                SUM(COUNT(p)) OVER (ORDER BY FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)) ASC) AS postCnt)
            FROM Post p
            GROUP BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt) AND p.categoryId
            ORDER BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt) ASC
            """)
    List<MonthlyCategoryPostCnt> getMonthlyCategoryPostCnt();

    // 일, 카테고리별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.DailyCategoryPostCnt(
            FROM
            """)
    List<DailyCategoryPostCnt> getDailyCategoryPostCnt();

}
