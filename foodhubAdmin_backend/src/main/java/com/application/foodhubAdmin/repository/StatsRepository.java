package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Stats;
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
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 1 " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')")
    List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt();

    // 일별 신규 가입자 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse(s.statDate, s.statCnt) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 1 " +
            "ORDER BY s.statDate")
    List<DailyNewUserCntResponse> getDailyNewUserCnt();


    // 년도별 신규 가입자 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.YearlyTotalUserCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 3 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyTotalUserCntResponse> getYearlyTotalUserCnt();

    // 월별 신규 가입자 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyTotalUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 3 " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')")
    List<MonthlyTotalUserCntResponse> getMonthlyTotalUserCnt();

    // 일별 신규 가입자 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.DailyTotalUserCntResponse(s.statDate, s.statCnt) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 3 " +
            "ORDER BY s.statDate")
    List<DailyTotalUserCntResponse> getDailyTotalUserCnt();

    // 년도별 탈퇴 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.YearlyDeleteUserCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 2 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyDeleteUserCntResponse> getYearlyDeleteUserCnt();

    // 월별 탈퇴 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyDeleteUserCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 2 " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')")
    List<MonthlyDeleteUserCntResponse> getMonthlyDeleteUserCnt();

    // 일별 탈퇴 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.user.DailyDeleteUserCntResponse(s.statDate, s.statCnt) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 2 " +
            "ORDER BY s.statDate")
    List<DailyDeleteUserCntResponse> getDailyDeleteUserCnt();

    /* 게시글 조회 */

    // 년도별 게시글 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.post.YearlyTotalPostCntResponse(YEAR(s.statDate), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 4 " +
            "GROUP BY YEAR(s.statDate) " +
            "ORDER BY YEAR(s.statDate)")
    List<YearlyTotalPostCntResponse> getYearlyTotalPostCnt();

    // 월별 게시글 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.post.MonthlyTotalPostCntResponse(FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), SUM(s.statCnt)) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 4 " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')")
    List<MonthlyTotalPostCntResponse> getMonthlyTotalPostCnt();

    // 일별 게시글 수
    @Query("SELECT new com.application.foodhubAdmin.dto.response.post.DailyTotalPostCntResponse(s.statDate, s.statCnt) " +
            "FROM Stats s " +
            "WHERE s.categoryId = 4 " +
            "ORDER BY s.statDate")
    List<DailyTotalPostCntResponse> getDailyTotalPostCnt();


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
        GROUP BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m'), s.categoryId
        ORDER BY FUNCTION('DATE_FORMAT', s.statDate, '%Y-%m')
    """)
    List<MonthlyCategoryPostCntResponse> getMonthlyCategoryPostCnt(@Param("categoryId") Integer categoryId);

    // 일별 통계
    @Query("""
        SELECT new com.application.foodhubAdmin.dto.response.post.DailyCategoryPostCntResponse(s.statDate , SUM(s.statCnt) , s.categoryId)
        FROM Stats s
        WHERE s.categoryId = :categoryId
        GROUP BY s.statDate, s.categoryId
        ORDER BY s.statDate
    """)
    List<DailyCategoryPostCntResponse> getDailyCategoryPostCnt(@Param("categoryId") Integer categoryId);





}
