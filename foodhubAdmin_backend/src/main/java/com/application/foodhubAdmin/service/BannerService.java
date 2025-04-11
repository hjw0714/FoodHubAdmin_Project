package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.dto.request.BannerRequest;
import com.application.foodhubAdmin.dto.response.banner.BannerResponse;
import com.application.foodhubAdmin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    @Value("${file.repo.path}")
    private String fileRepositoryPath;

    public List<BannerResponse> findAll() {
        return bannerRepository.findAll().stream()
                .map(BannerResponse::of)
                .toList();
    }

    @Transactional
    public void saveBanner(BannerRequest requestDto, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String originalFileName = requestDto.getBannerOriginalName();
            requestDto.setBannerOriginalName(originalFileName);

            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

            String uploadFile = UUID.randomUUID() + extension;
            requestDto.setBannerUuid(uploadFile);

            imageFile.transferTo(new File(fileRepositoryPath + uploadFile));
        }
        bannerRepository.save(requestDto.toEntity());
    }


}
