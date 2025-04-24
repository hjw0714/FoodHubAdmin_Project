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

	@Query("SELECT COUNT(u) FROM User u " +
			"WHERE FUNCTION('DATE', u.joinAt) = :date")
	Long countUserJoinedOn(@Param("date") LocalDate date); // 유저 가입수 통계저장

	@Query("SELECT COUNT(u) FROM User u " +
			"WHERE FUNCTION('DATE', u.deletedAt) = :date")
	Long countUserDeletedOn(@Param("date") LocalDate date); // 유저 탈퇴수 통계저장

	@Query("SELECT COUNT(u) FROM User u WHERE u.deletedAt IS NULL")
	Long countTotalUsers();	// 유저 통합수 통계저장

	@Query("SELECT u FROM User u WHERE u.status = 'deleted' AND u.deletedAt <= :threshold")
	List<User> findUsersToHardDelete(@Param("threshold") LocalDateTime threshold);

	@Query("SELECT u.nickname FROM User u WHERE u.id = :userId")
    String getNicknameByUserId(String userId);


    //List<User> id(String id);

}
