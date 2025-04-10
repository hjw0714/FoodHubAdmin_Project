package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.dto.response.post.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMsRepository extends JpaRepository<Post, Long>{

    // 년별 작성된 게시글 수
    @Query("""
    SELECT new com.application.foodhubAdmin.dto.response.post.YearlyNewPostCntResponse(
        FUNCTION('YEAR', p.createdAt) AS YEAR,
        COUNT(p) AS postCnt)
    FROM Post p
    GROUP BY FUNCTION('YEAR', p.createdAt)
    ORDER BY FUNCTION('YEAR', p.createdAt) ASC    
    """)
    List<YearlyNewPostCntResponse> getYearlyNewPostCnt();

    // 월별 작성된 게시글 수
    @Query("""
    SELECT new com.application.foodhubAdmin.dto.response.post.MonthlyNewPostCntResponse(
        CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)),
        COUNT(p))
    FROM Post p
    GROUP BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt))
    ORDER BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)) ASC
    """)
    List<MonthlyNewPostCntResponse> getMonthlyNewPostCnt();

    // 일별 작성된 게시글 수
    @Query("""
    SELECT new com.application.foodhubAdmin.dto.response.post.DailyNewPostCntResponse(
            CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt) , '-', FUNCTION('DATE', p.createdAt)),
            COUNT(p))
    FROM Post p
    GROUP BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt) , '-', FUNCTION('DATE', p.createdAt))
    ORDER BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt) , '-', FUNCTION('DATE', p.createdAt))ASC
        """)
    List<DailyNewPostCntResponse> getDailyNewPostCnt();


    // 연도, 카테고리별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.post.YearlyCategoryPostCntResponse(
                YEAR(p.createdAt) as year,
                p.subCateNm AS subCateNm,
                COUNT(p) AS postCnt)
            FROM Post p
            GROUP BY YEAR(p.createdAt), p.subCateNm
            ORDER BY YEAR(p.createdAt) ASC
            """)
    List<YearlyCategoryPostCntResponse> getYearlyCategoryPostCnt();

    // 월, 카테고리별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.post.MonthlyCategoryPostCntResponse(
                CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)),
                p.subCateNm,
                COUNT(p))
            FROM Post p
            GROUP BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)), p.subCateNm
            ORDER BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)) ASC
            """)
    List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt();

    // 일, 카테고리별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.post.DailyCategoryPostCntResponse(
                FUNCTION('DATE', p.createdAt) AS day,
                p.subCateNm AS subCateNm,
                COUNT(p) AS postCnt)
            FROM Post p
            GROUP BY FUNCTION('DATE', p.createdAt), p.subCateNm
            ORDER BY FUNCTION('DATE', p.createdAt) ASC
            """)
    List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt();



}
