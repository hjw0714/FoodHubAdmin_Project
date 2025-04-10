package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.dto.response.post.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostMsRepository extends JpaRepository<Post, Long>{

    @Query("SELECT COUNT(p) FROM Post p WHERE p.status = 'ACTIVE'")
    Long countTotalPosts();	// 게시글 통합수 통계저장

    @Query("SELECT COUNT(p) FROM Post p WHERE p.categoryId = :findPostCategoryId AND p.status = 'ACTIVE'")
    Long countTotalPostsByPostCategoryId(@Param("findPostCategoryId") Integer findPostCategoryId);	// 카테고리별 총 게시글 통계저장



}
