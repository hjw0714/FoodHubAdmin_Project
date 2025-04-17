package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentReportRepository extends JpaRepository<CommentReport , Long> {

    @Query("""
           SELECT count(cr)
           FROM CommentReport cr
           """)
    Long countCommentReport();

}
