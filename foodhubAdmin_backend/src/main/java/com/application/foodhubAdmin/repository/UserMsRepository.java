package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserMsRepository extends JpaRepository <User, String> {

//	// 년도별 신규 가입자 수
//	@Query("""
//    SELECT new com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse(
//        FUNCTION('YEAR', u.joinAt) AS YEAR,
//        COUNT(u) AS userCnt)
//    FROM User u
//    GROUP BY FUNCTION('YEAR', u.joinAt)
//    ORDER BY YEAR ASC
//    """)
//	List<YearlyNewUserCntResponse> getYearlyNewUserCnt();
//
//	// 월별 신규 가입자 수
//    @Query("""
//		SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse(
//			CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt)),
//			COUNT(u))
//		FROM User u
//		GROUP BY CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt))
//		ORDER BY CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt)) ASC
//	""")
//    List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt();
//
//	// 일별 신규 가입자 수
//	@Query("""
//    SELECT new com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse(
//            CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt) , '-', FUNCTION('DATE', u.joinAt)),
//            COUNT(u))
//    FROM User u
//    GROUP BY CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt) , '-', FUNCTION('DATE', u.joinAt))
//    ORDER BY CONCAT(FUNCTION('YEAR', u.joinAt), '-', FUNCTION('MONTH', u.joinAt) , '-', FUNCTION('DATE', u.joinAt))ASC
//        """)
//	List<DailyNewUserCntResponse> getDailyNewUserCnt();

//	@Query("""
//    SELECT new com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse(
//        FUNCTION('YEAR', s.term),
//        SUM(s.joinCnt))
//    FROM Stats s
//    WHERE s.cate = 'USER'
//    GROUP BY FUNCTION('YEAR', s.term)
//    ORDER BY FUNCTION('YEAR', s.term) ASC
//	""")
//	List<YearlyNewUserCntResponse> getYearlyNewUserCnt();
//
//	@Query("""
//    SELECT new com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse(
//        CONCAT(FUNCTION('YEAR', s.term), '-', LPAD(FUNCTION('MONTH', s.term), 2, '0')),
//        SUM(s.joinCnt))
//    FROM Stats s
//    WHERE s.cate = 'USER'
//    GROUP BY FUNCTION('YEAR', s.term), FUNCTION('MONTH', s.term)
//    ORDER BY FUNCTION('YEAR', s.term), FUNCTION('MONTH', s.term) ASC
//	""")
//	List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt();
//
//	@Query("""
//    SELECT new com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse(
//        s.term,
//        s.joinCnt)
//    FROM Stats s
//    WHERE s.cate = 'USER'
//    ORDER BY s.term ASC
//	""")
//	List<DailyNewUserCntResponse> getDailyNewUserCnt();


//	@Query("""
//	SELECT COUNT(u)
//	FROM User u
//	WHERE u.joinAt BETWEEN :start AND :end
//	""")
//	Long countByJoinAtBetween(LocalDateTime start, LocalDateTime end);
//
//	@Query("""
//	SELECT COUNT(u)
//	FROM User u
//	WHERE u.deletedAt IS NOT NULL
//	AND u.deletedAt
//	BETWEEN :start AND :end
//	""")
//	Long countByDeletedAtBetween(LocalDateTime start, LocalDateTime end);


	@Query("SELECT COUNT(u) FROM User u " +
			"WHERE FUNCTION('DATE', u.joinAt) = :date")
	Long countUserJoinedOn(@Param("date") LocalDate date); // 유저 가입수 통계저장

	@Query("SELECT COUNT(u) FROM User u " +
			"WHERE FUNCTION('DATE', u.deletedAt) = :date")
	Long countUserDeletedOn(@Param("date") LocalDate date); // 유저 탈퇴수 통계저장


	@Query("SELECT COUNT(u) FROM User u WHERE u.deletedAt IS NULL")
	Long countTotalUsers();	// 유저 통합수 통계저장







	List<User> id(String id);

}
