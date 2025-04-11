package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.dto.response.comments.DailyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.YearlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.dto.response.user.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {
    Optional<Stats> findByCategoryIdAndStatDate(Integer categoryId, LocalDate statDate);

    /* 유저 조회 */

    // 년도별 신규 가입자 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 1 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyNewUserCntResponse> getYearlyNewUserCnt();

    // 월별 신규 가입자 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 1
            AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt(@Param("startDate") String startDate);

    // 일별 신규 가입자 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 1
            AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d') >= :startDate
            ORDER BY s.statDate""")
    List<DailyNewUserCntResponse> getDailyNewUserCnt(@Param("startDate") String startDate);


    // 년도별 신규 가입자 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.YearlyTotalUserCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 3 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyTotalUserCntResponse> getYearlyTotalUserCnt();

    // 월별 신규 가입자 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyTotalUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 3
            AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            """)
    List<MonthlyTotalUserCntResponse> getMonthlyTotalUserCnt(@Param("startDate") String startDate);

    // 일별 신규 가입자 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.DailyTotalUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 3
            AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d') >= :startDate
            ORDER BY s.statDate""")
    List<DailyTotalUserCntResponse> getDailyTotalUserCnt(@Param("startDate") String startDate);

    // 년도별 탈퇴 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.YearlyDeleteUserCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 2 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyDeleteUserCntResponse> getYearlyDeleteUserCnt();

    // 월별 탈퇴 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyDeleteUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 2
            AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyDeleteUserCntResponse> getMonthlyDeleteUserCnt(@Param("startDate") String startDate);

    // 일별 탈퇴 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.DailyDeleteUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 2
            AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d') >= :startDate
            ORDER BY s.statDate""")
    List<DailyDeleteUserCntResponse> getDailyDeleteUserCnt(@Param("startDate") String startDate);

    /* 게시글 조회 */

    // 년도별 게시글 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.post.YearlyTotalPostCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 4 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyTotalPostCntResponse> getYearlyTotalPostCnt();

    // 월별 게시글 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.post.MonthlyTotalPostCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 4
            AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyTotalPostCntResponse> getMonthlyTotalPostCnt(@Param("startDate") String startDate);

    // 일별 게시글 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.post.DailyTotalPostCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 4
            AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d') >= :startDate
            ORDER BY s.statDate""")
    List<DailyTotalPostCntResponse> getDailyTotalPostCnt(@Param("startDate") String startDate);

    // 연도별 통계
    @Query("""
        SELECT new com.application.foodhubAdmin.dto.response.post.YearlyCategoryPostCntResponse(YEAR(s.statDate), SUM(s.statCnt) , s.categoryId)
        FROM Stats s
        WHERE s.categoryId = :categoryId
        GROUP BY YEAR(s.statDate)
        ORDER BY YEAR(s.statDate)
    """)
    List<YearlyCategoryPostCntResponse> getYearlyCategoryPostCnt(@Param("categoryId") Integer categoryId);


    // 월별 통계
    @Query("""
        SELECT new com.application.foodhubAdmin.dto.response.post.MonthlyCategoryPostCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt) , s.categoryId)
        FROM Stats s
        WHERE s.categoryId = :categoryId
        AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') >= :startDate
        GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), s.categoryId
        ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
    """)
    List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt(@Param("categoryId") Integer categoryId, @Param("startDate") String startDate);

    // 일별 통계
    @Query("""
        SELECT new com.application.foodhubAdmin.dto.response.post.DailyCategoryPostCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d') , SUM(s.statCnt) , s.categoryId)
        FROM Stats s
        WHERE s.categoryId = :categoryId
        AND FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d') >= :startDate
        GROUP BY s.statDate, s.categoryId
        ORDER BY s.statDate
    """)
    List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt(@Param("categoryId") Integer categoryId, @Param("startDate") String startDate);

    /*댓글 조회*/

    // 년도별 총 댓글 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.comments.YearlyTotalCommentsCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 13 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyTotalCommentsCntResponse> getYearlyTotalCommentsCnt();

    // 월별 총 댓글 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.comments.MonthlyTotalCommentsCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 13 " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')")
    List<MonthlyTotalCommentsCntResponse> getMonthlyTotalCommentsCnt();

    // 일별 총 댓글 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.comments.DailyTotalCommentsCntResponse(s.statDate, s.statCnt) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 13 " +
            "ORDER BY s.statDate")
    List<DailyTotalCommentsCntResponse> getDailyTotalCommentsCnt();



}
