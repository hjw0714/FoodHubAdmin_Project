package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCreateRepository extends JpaRepository<Post , Long> {
}
