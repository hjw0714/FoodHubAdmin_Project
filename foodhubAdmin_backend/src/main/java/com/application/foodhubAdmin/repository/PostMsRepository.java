package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.dto.response.post.DailyNewPostCntResponse;
import com.application.foodhubAdmin.dto.response.post.MonthlyNewPostCntResponse;
import com.application.foodhubAdmin.dto.response.post.YearlyNewPostCntResponse;
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
    ORDER BY YEAR ASC    
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




}
