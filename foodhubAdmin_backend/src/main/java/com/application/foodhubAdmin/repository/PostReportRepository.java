package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport , Long> {

    @Query("""
    SELECT COUNT(pr)
    FROM   PostReport pr
    WHERE FUNCTION('DATE' , pr.createdAt) = :date 
    """)
    Long countPostReport(@Param("date") LocalDate date);

}
