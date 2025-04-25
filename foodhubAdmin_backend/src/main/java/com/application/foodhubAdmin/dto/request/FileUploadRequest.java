package com.application.foodhubAdmin.dto.request;

import com.application.foodhubAdmin.domain.FileUpload;
import com.application.foodhubAdmin.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FileUploadRequest {

    private Long id;
    private Post post;
    private String fileUUid;
    private String fileName;
    private String filePath;

    public FileUpload toEntity(String fileUuid, String filePath, String originalName, Post post) {
        return FileUpload.builder()
                .post(post)
                .fileUuid(fileUuid)
                .filePath(filePath)
                .fileName(originalName)
                .uploadDate(LocalDateTime.now())
                .build();
    }
}
