package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.PostReport;
import com.application.foodhubAdmin.dto.response.post.MonthlyPostReportResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport , Long> {

    @Query("""
           select count(*)
           From PostReport r
           GROUP BY FUNCTION('DATE_FORMAT', r.createdAt, '%Y-%m')
           ORDER BY FUNCTION('DATE_FORMAT', r.createdAt, '%Y-%m')
           """)
    List<MonthlyPostReportResponse> getPostReportCnt();

}
