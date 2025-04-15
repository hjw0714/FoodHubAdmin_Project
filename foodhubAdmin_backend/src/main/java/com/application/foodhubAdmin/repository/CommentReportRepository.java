package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.CommentReport;
import com.application.foodhubAdmin.dto.response.comments.MonthlyCommentReportResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentReportRepository extends JpaRepository<CommentReport , Long> {

    @Query("""
           select count(*)
           from CommentReport r
           GROUP BY FUNCTION('DATE_FORMAT', r.createdAt, '%Y-%m')
           ORDER BY FUNCTION('DATE_FORMAT', r.createdAt, '%Y-%m')
            """)
    List<MonthlyCommentReportResponse> getCommentReportCnt();

}
