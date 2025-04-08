package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.dto.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMsRepository extends JpaRepository<Post, Long> {

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
                COUNT(p))
            FROM Post p
            GROUP BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt))
            ORDER BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)) ASC
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
                p.subCateNm as subCateNm,
                COUNT(p) AS postCnt)
            FROM Post p
            GROUP BY YEAR(p.createdAt), p.subCateId
            ORDER BY YEAR(p.createdAt) ASC
            """)
    List<YearlyCategoryPostCnt> getYearlyCategoryPostCnt();

    // 월, 카테고리별 총 게시글
//    @Query("""
//            SELECT new com.application.foodhubAdmin.dto.response.MonthlyCategoryPostCnt(
//                CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)),
//                p.subCateNm AS subCateNm,
//                COUNT(p))
//            FROM Post p
//            GROUP BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)), p.subCateId
//            ORDER BY CONCAT(FUNCTION('YEAR', p.createdAt), '-', FUNCTION('MONTH', p.createdAt)) ASC
//            """)
//    List<MonthlyCategoryPostCnt> getMonthlyCategoryPostCnt();

    // 일, 카테고리별 총 게시글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.DailyCategoryPostCnt(
                  FUNCTION('DATE', p.createdAt) AS day,
                  p.subCateNm AS subCateNm,
                  SUM(COUNT(p)) OVER (ORDER BY FUNCTION('DATE', p.createdAt) ASC) AS postCnt)
            FROM Post p
            GROUP BY FUNCTION('DATE', p.createdAt), p.subCateId
            ORDER BY FUNCTION('DATE', p.createdAt) ASC
            """)
    List<DailyCategoryPostCnt> getDailyCategoryPostCnt();

}
