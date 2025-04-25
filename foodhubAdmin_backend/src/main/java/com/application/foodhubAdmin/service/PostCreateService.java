package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.FileUpload;
import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.request.FileUploadRequest;
import com.application.foodhubAdmin.dto.request.PostCreateRequest;
import com.application.foodhubAdmin.repository.FileUploadRepository;
import com.application.foodhubAdmin.repository.PostCreateRepository;
import com.application.foodhubAdmin.repository.UserMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostCreateService {

    private final PostCreateRepository postCreateRepository;
    private final FileUploadRepository fileUploadRepository;
    private final UserMsRepository userMsRepository;

    @Value("${file.repo.path}")
    private String fileRepositoryPath;

    public void createPost(PostCreateRequest postCreateRequest) throws IOException {

        User user = userMsRepository.findById(postCreateRequest.getUserId()).orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        Post post = postCreateRepository.save(postCreateRequest.toEntity(user));

        MultipartFile file = postCreateRequest.getFile();
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String fileUuid = UUID.randomUUID() + extension;

                String filePath = fileRepositoryPath + fileUuid;

                file.transferTo(new File(filePath));

                FileUploadRequest fileRequest = new FileUploadRequest(); // 직접 생성
                FileUpload fileUpload = fileRequest.toEntity(fileUuid, filePath, originalFileName, post);
                fileUploadRepository.save(fileUpload);

            } catch(IOException e) {
                throw new RuntimeException("파일 저장에 실패했습니다.");
            }
        }
    }

}
