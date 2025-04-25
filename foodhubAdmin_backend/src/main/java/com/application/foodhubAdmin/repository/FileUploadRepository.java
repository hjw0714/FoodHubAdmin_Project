package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload , Long> {
}
