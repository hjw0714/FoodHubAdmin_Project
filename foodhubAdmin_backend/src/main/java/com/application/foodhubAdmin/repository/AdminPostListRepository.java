package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPostListRepository extends JpaRepository<Post , Long> {

    List<Post> findByCategoryNm(String categoryNm);

}
