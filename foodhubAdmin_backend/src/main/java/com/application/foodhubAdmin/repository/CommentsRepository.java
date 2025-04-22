
package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("SELECT COUNT(c) FROM Comments c WHERE c.status = 'VISIBLE' AND FUNCTION('DATE' , c.createdAt) = :date")
    Long countTotalComments(@Param("date")LocalDate date); // 댓글 총 수 저장


}
