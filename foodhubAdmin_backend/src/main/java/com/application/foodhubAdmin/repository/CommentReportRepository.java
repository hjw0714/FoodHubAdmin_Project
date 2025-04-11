package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport , Long> {
}
