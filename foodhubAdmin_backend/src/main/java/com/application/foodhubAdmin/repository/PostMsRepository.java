package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.dto.response.YearlyTotalPostCntResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMsRepository extends JpaRepository<Post, Long> {

    @Query("""
            select com.application.foodhubAdmin.dto.response.YearlyTotalPostCntResponse(
                CONCAT(YEAR(p.createdAt)) as year,
                COUNT(p) as yearlyPostCnt)
            FROM Post p
            GROUP BY CONCAT(YEAR(p.createdAt))
            ORDER BY CONCAT(YEAR(p.createdAt)) ASC
            """)
    List<YearlyTotalPostCntResponse> getYearlyTotalPostCnt();

}
