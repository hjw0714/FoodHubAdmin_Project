package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport , Long> {

    @Query("""
           SELECT count(pr)
           FROM PostReport pr
           """)
    Long countPostReport();

}
