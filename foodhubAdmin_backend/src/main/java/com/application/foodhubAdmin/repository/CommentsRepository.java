package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Comments;
import com.application.foodhubAdmin.dto.response.comments.DailyNewCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyNewCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.YearlyNewCommentsCntResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    // 연도별 새 댓글
    @Query("""
            SELECT  new com.application.foodhubAdmin.dto.response.comments.YearlyNewCommentsCntResponse(
                FUNCTION('YEAR', c.createdAt) AS year,
                COUNT(c) AS commentsCnt)
            FROM Comments c
            GROUP BY FUNCTION('YEAR', c.createdAt)
            ORDER BY FUNCTION('YEAR', c.createdAt) ASC
            """)
    List<YearlyNewCommentsCntResponse> getYearlyNewCommentsCnt();


    // 월별 새 댓글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.comments.MonthlyNewCommentsCntResponse(
                CONCAT(FUNCTION('YEAR', c.createdAt), '-', FUNCTION('MONTH', c.createdAt)),
                COUNT(c))
            FROM Comments c
            GROUP BY CONCAT(FUNCTION('YEAR', c.createdAt), '-', FUNCTION('MONTH', c.createdAt))
            ORDER BY CONCAT(FUNCTION('YEAR', c.createdAt), '-', FUNCTION('MONTH', c.createdAt)) ASC
            """)
    List<MonthlyNewCommentsCntResponse> getMonthlyNewCommentsCnt();

    // 일별 새 댓글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.comments.DailyNewCommentsCntResponse(
                FUNCTION('DATE', c.createdAt) AS day,
                COUNT(c))
            FROM Comments c
            GROUP BY FUNCTION('DATE', c.createdAt)
            ORDER BY FUNCTION('DATE', c.createdAt) ASC
            """)
    List<DailyNewCommentsCntResponse> getDailyNewCommentsCnt();

}
