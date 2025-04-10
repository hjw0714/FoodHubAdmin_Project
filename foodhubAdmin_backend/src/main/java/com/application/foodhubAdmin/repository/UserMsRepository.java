package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.response.post.DailyNewPostCntResponse;
import com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMsRepository extends JpaRepository <User, String> {

	// 년도별 신규 가입자 수
	@Query("""
    SELECT new com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse(
        FUNCTION('YEAR', u.joinAt) AS YEAR,
        COUNT(u) AS userCnt)
    FROM User u
    GROUP BY FUNCTION('YEAR', u.joinAt)
    ORDER BY YEAR ASC    
    """)
	List<YearlyNewUserCntResponse> getYearlyNewUserCnt();

	// 월별 신규 가입자 수
    @Query("""
		SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse(
			CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt)),
			COUNT(u))
		FROM User u
		GROUP BY CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt))
		ORDER BY CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt)) ASC
	""")
    List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt();

	// 일별 신규 가입자 수
	@Query("""
    SELECT new com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse(
            CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt) , '-', FUNCTION('DATE', u.joinAt)),
            COUNT(u))
    FROM User u
    GROUP BY CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt) , '-', FUNCTION('DATE', u.joinAt))
    ORDER BY CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt) , '-', FUNCTION('DATE', u.joinAt))ASC
        """)
	List<DailyNewUserCntResponse> getDailyNewUserCnt();







}
