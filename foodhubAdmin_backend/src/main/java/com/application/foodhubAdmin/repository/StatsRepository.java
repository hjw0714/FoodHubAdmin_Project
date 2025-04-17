package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.dto.response.comments.DailyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyCommentReportResponse;
import com.application.foodhubAdmin.dto.response.comments.MonthlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.comments.YearlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.post.*;
import com.application.foodhubAdmin.dto.response.user.*;
import com.application.foodhubAdmin.dto.response.visitorLog.DailyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.YearlyVisitorLogResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
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
            AND CONCAT(FUNCTION('YEAR', s.statDate), '-', FUNCTION('MONTH', s.statDate)) >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt(@Param("startDate") String startDate);

    // 일별 신규 가입자 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 1
            AND FUNCTION('DATE', s.statDate) >= :parsedStartDate
            ORDER BY s.statDate""")
    List<DailyNewUserCntResponse> getDailyNewUserCnt(@Param("parsedStartDate") Date parsedStartDate);


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
            AND CONCAT(FUNCTION('YEAR', s.statDate), '-', FUNCTION('MONTH', s.statDate)) >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            """)
    List<MonthlyTotalUserCntResponse> getMonthlyTotalUserCnt(@Param("startDate") String startDate);

    // 일별 신규 가입자 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.DailyTotalUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 3
            AND FUNCTION('DATE', s.statDate) >= :parsedStartDate
            ORDER BY s.statDate""")
    List<DailyTotalUserCntResponse> getDailyTotalUserCnt(@Param("parsedStartDate") Date parsedStartDate);

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
            AND CONCAT(FUNCTION('YEAR', s.statDate), '-', FUNCTION('MONTH', s.statDate)) >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyDeleteUserCntResponse> getMonthlyDeleteUserCnt(@Param("startDate") String startDate);

    // 일별 탈퇴 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.DailyDeleteUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 2
            AND FUNCTION('DATE', s.statDate) >= :parsedStartDate
            ORDER BY s.statDate""")
    List<DailyDeleteUserCntResponse> getDailyDeleteUserCnt(@Param("parsedStartDate") Date parsedStartDate);

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
            AND CONCAT(FUNCTION('YEAR', s.statDate), '-', FUNCTION('MONTH', s.statDate)) >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyTotalPostCntResponse> getMonthlyTotalPostCnt(@Param("startDate") String startDate);

    // 일별 게시글 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.post.DailyTotalPostCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 4
            AND FUNCTION('DATE', s.statDate) >= :parsedStartDate
            ORDER BY s.statDate""")
    List<DailyTotalPostCntResponse> getDailyTotalPostCnt(@Param("parsedStartDate") Date parsedStartDate);

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
        AND CONCAT(FUNCTION('YEAR', s.statDate), '-', FUNCTION('MONTH', s.statDate)) >= :startDate
        GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), s.categoryId
        ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
    """)
    List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt(@Param("categoryId") Integer categoryId, @Param("startDate") String startDate);

    // 일별 통계
    @Query("""
        SELECT new com.application.foodhubAdmin.dto.response.post.DailyCategoryPostCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d') , SUM(s.statCnt) , s.categoryId)
        FROM Stats s
        WHERE s.categoryId = :categoryId
        AND FUNCTION('DATE', s.statDate) >= :parsedStartDate
        GROUP BY s.statDate, s.categoryId
        ORDER BY s.statDate
    """)
    List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt(@Param("categoryId") Integer categoryId, @Param("parsedStartDate") Date parsedStartDate);

    /*댓글 조회*/

    // 년도별 총 댓글 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.comments.YearlyTotalCommentsCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 13 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyTotalCommentsCntResponse> getYearlyTotalCommentsCnt();

    // 월별 총 댓글 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.comments.MonthlyTotalCommentsCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 13
            AND CONCAT(FUNCTION('YEAR', s.statDate), '-', FUNCTION('MONTH', s.statDate)) >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyTotalCommentsCntResponse> getMonthlyTotalCommentsCnt(@Param("startDate") String startDate);

    // 일별 총 댓글 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.comments.DailyTotalCommentsCntResponse(s.statDate, s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 13
            AND FUNCTION('DATE', s.statDate) >= :parsedStartDate
            ORDER BY s.statDate""")
    List<DailyTotalCommentsCntResponse> getDailyTotalCommentsCnt(@Param("parsedStartDate") Date parsedStartDate);

    // 연도별 방문자수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.visitorLog.YearlyVisitorLogResponse(YEAR(s.statDate), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 14
            GROUP BY YEAR(s.statDate)
            ORDER BY YEAR(s.statDate)
            """)
    List<YearlyVisitorLogResponse> getYearlyVisitorLog();

    // 월별 방문자 수
    @Query("""
           SELECT new com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 14
            AND CONCAT(FUNCTION('YEAR', s.statDate), '-', FUNCTION('MONTH', s.statDate)) >= :startDate
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
           """)
    List<MonthlyVisitorLogResponse> getMonthlyVisitorLog(@Param("startDate") String startDate);

    // 일별 방문자 수
    @Query("""
           SELECT new com.application.foodhubAdmin.dto.response.visitorLog.DailyVisitorLogResponse(
                  FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m-%d'), s.statCnt)
            FROM Stats s
            WHERE s.categoryId = 14
            AND FUNCTION('DATE', s.statDate) >= :parsedStartDate
            ORDER BY s.statDate
           """)
    List<DailyVisitorLogResponse> getDailyVisitorLog(@Param("parsedStartDate") Date parsedStartDate);

    // 대시보드 신규 가입자
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 1
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyNewUserCntResponse> getNewUserCnt();

    // 대시보드 탈퇴 수
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyDeleteUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 2
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyDeleteUserCntResponse> getDeleteUserCnt();

    // 대시보드 게시물
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.post.MonthlyTotalPostCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 4
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyTotalPostCntResponse> getTotalPostCnt();

    // 대시보드 댓글
    @Query("""
            SELECT new com.application.foodhubAdmin.dto.response.comments.MonthlyTotalCommentsCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 13
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')""")
    List<MonthlyTotalCommentsCntResponse> getTotalCommentsCnt();

    // 대시보드 방문자 수
    @Query("""
           SELECT new com.application.foodhubAdmin.dto.response.visitorLog.MonthlyVisitorLogResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 14
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
           """)
    List<MonthlyVisitorLogResponse> getVisitorLog();

    // 대시보드 게시글 신고 수
    @Query("""
           SELECT new com.application.foodhubAdmin.dto.response.post.MonthlyPostReportResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 15
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
           """)
    List<MonthlyPostReportResponse> getPostReportCnt();

    // 대시보드 댓글 신고 수
    @Query("""
           SELECT new com.application.foodhubAdmin.dto.response.comments.MonthlyCommentReportResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt))
            FROM Stats s
            WHERE s.categoryId = 16
            GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
            ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
           """)
    List<MonthlyCommentReportResponse> getCommentReportCnt();

}
