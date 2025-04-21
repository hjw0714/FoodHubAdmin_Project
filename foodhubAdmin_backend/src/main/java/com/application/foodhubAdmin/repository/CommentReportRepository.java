package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface CommentReportRepository extends JpaRepository<CommentReport , Long> {

    @Query("""
    SELECT COUNT(cr)
    FROM   CommentReport cr
    WHERE FUNCTION('DATE' , cr.createdAt) = :date 
    """)
    Long countCommentReport(@Param("date") LocalDate date);
}
