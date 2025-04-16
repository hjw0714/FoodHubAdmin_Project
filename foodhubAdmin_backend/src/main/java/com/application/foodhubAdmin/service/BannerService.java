package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.Banner;
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
import java.time.LocalDateTime;
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
            // 1. 원본 파일명 추출
            String originalFileName = imageFile.getOriginalFilename(); // ← 이게 정확함
            requestDto.setBannerOriginalName(originalFileName);

            // 2. 확장자 추출
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 3. UUID로 새 파일 이름 생성
            String uploadFile = UUID.randomUUID() + extension;
            requestDto.setBannerUuid(uploadFile);

            // 4. 파일 저장
            imageFile.transferTo(new File(fileRepositoryPath + uploadFile));
        }

        // 5. Entity 생성 및 시간 세팅
        Banner banner = requestDto.toEntity();
        banner.setCreatedAt(LocalDateTime.now());
        banner.setUpdatedAt(LocalDateTime.now());

        // 6. 저장
        bannerRepository.save(banner);
    }

    @Transactional
    public void delete(Long id) {
        bannerRepository.deleteById(id);
    }

    @Transactional
    public void updateBanner(Long id, BannerRequest requestDto, MultipartFile imageFile) throws IOException {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 배너를 찾을 수 없습니다. id: " + id));
        String originalFileName = null;
        String uploadFile= null;

        // 이미지가 새로 추가된 경우
        if (imageFile != null && !imageFile.isEmpty()) {
            new File(fileRepositoryPath + requestDto.getBannerUuid()).delete();

            originalFileName = imageFile.getOriginalFilename();
            requestDto.setBannerOriginalName(originalFileName);

            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

            uploadFile = UUID.randomUUID() + extension;
            requestDto.setBannerUuid(uploadFile);

            imageFile.transferTo(new File(fileRepositoryPath + uploadFile));

        }
        banner.updateBanner(requestDto.getTitle(), requestDto.getDescription(), originalFileName , uploadFile , requestDto.getLink());
        bannerRepository.save(banner);
    }


}
