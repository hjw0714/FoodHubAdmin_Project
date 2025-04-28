package com.application.foodhubAdmin.banner;

import com.application.foodhubAdmin.domain.Banner;
import com.application.foodhubAdmin.dto.request.BannerRequest;
import com.application.foodhubAdmin.dto.response.banner.BannerResponse;
import com.application.foodhubAdmin.dto.response.post.PostReportResponse;
import com.application.foodhubAdmin.repository.BannerRepository;
import com.application.foodhubAdmin.service.BannerService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BannerServiceTest {

    @Mock
    private BannerRepository bannerRepository;

    @InjectMocks
    private BannerService bannerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("배너 목록 조회")
    void findAllBanners() {
        System.out.println("1. 배너 목록 조회");

        Banner banner1 = Banner.builder()
                .id(1L)
                .title("테스트 배너1")
                .description("테스트 설명1")
                .link("http://link1.com")
                .bannerOriginalName("original1.png")
                .bannerUuid("uuid1.png")
                .createdAt(LocalDateTime.now())
                .build();

        Banner banner2 = Banner.builder()
                .id(2L)
                .title("테스트 배너2")
                .description("테스트 설명2")
                .link("http://link2.com")
                .bannerOriginalName("original2.png")
                .bannerUuid("uuid2.png")
                .createdAt(LocalDateTime.now())
                .build();

        Banner banner3 = Banner.builder()
                .id(3L)
                .title("테스트 배너3")
                .description("테스트 설명3")
                .link("http://link3.com")
                .bannerOriginalName("original3.png")
                .bannerUuid("uuid3.png")
                .createdAt(LocalDateTime.now())
                .build();

        when(bannerRepository.findAll()).thenReturn(List.of(banner1 , banner2 , banner3));

        List<BannerResponse> result = bannerService.findAll();

        assertEquals(3, result.size());
        assertEquals("테스트 배너1", result.get(0).getTitle());
        assertEquals("테스트 배너2", result.get(1).getTitle());
        assertEquals("테스트 배너3", result.get(2).getTitle());

        result.forEach(r -> {
            System.out.println("배너 ID: " + r.getId());
            System.out.println("배너 제목: " + r.getTitle());
            System.out.println("배너 설명: " + r.getDescription());
            System.out.println("배너 링크: " + r.getLink());
            System.out.println("파일 원본 이름: " + r.getBannerOriginalName());
            System.out.println("파일 UUID: " + r.getBannerUuid());
            System.out.println("배너 등록 일자: " + r.getCreatedAt());
            System.out.println("==============================");
        });
        System.out.println();

    }
    
    @Test
    @Order(2)
    @DisplayName("배너 삭제")
    void saveBanner() {
        System.out.println("2. 배너 삭제 테스트");

        Banner banner1 = Banner.builder()
                .id(1L)
                .title("테스트 배너1")
                .description("테스트 설명1")
                .link("http://link1.com")
                .bannerOriginalName("original1.png")
                .bannerUuid("uuid1.png")
                .createdAt(LocalDateTime.now())
                .build();

        when(bannerRepository.findById(banner1.getId())).thenReturn(Optional.of(banner1));

        // 삭제 전 배너 정보 출력
        Optional<Banner> beforeDelete = bannerRepository.findById(banner1.getId());
        beforeDelete.ifPresent(b -> {
            System.out.println("삭제 전 배너 정보:");
            System.out.println("ID: " + b.getId());
            System.out.println("제목: " + b.getTitle());
            System.out.println("설명: " + b.getDescription());
            System.out.println("링크: " + b.getLink());
            System.out.println("파일명: " + b.getBannerOriginalName());
            System.out.println("UUID: " +b.getBannerUuid());
            System.out.println("==============================");
        });

        // when
        bannerService.delete(banner1.getId());

        // then
        verify(bannerRepository, times(1)).deleteById(banner1.getId());

        // 삭제 후 출력
        System.out.println("삭제 후: 배너가 삭제되었습니다 (Mock 환경에서는 직접 확인 불가)");

        System.out.println();

    }

    @Test
    @Order(3)
    @DisplayName("배너 수정")
    void updateBanner() throws IOException, IOException {
        System.out.println("3. 배너 수정 테스트");

        // given
        Banner existingBanner = Banner.builder()
                .id(1L)
                .title("기존 배너")
                .description("기존 설명")
                .link("http://old-link.com")
                .bannerOriginalName("old_original.png")
                .bannerUuid("old_uuid.png")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        BannerRequest updateRequest = new BannerRequest();
        updateRequest.setTitle("수정된 배너");
        updateRequest.setDescription("수정된 설명");
        updateRequest.setLink("http://new-link.com");

        MockMultipartFile newImage = new MockMultipartFile(
                "file",
                "new_image.png",
                "image/png",
                "이미지".getBytes()
        );

        when(bannerRepository.findById(1L)).thenReturn(Optional.of(existingBanner));

        // 수정 전 배너 정보 출력
        System.out.println("수정 전 배너 정보:");
        System.out.println("ID: " + existingBanner.getId());
        System.out.println("제목: " + existingBanner.getTitle());
        System.out.println("설명: " + existingBanner.getDescription());
        System.out.println("링크: " + existingBanner.getLink());
        System.out.println("파일명: " + existingBanner.getBannerOriginalName());
        System.out.println("UUID: " + existingBanner.getBannerUuid());
        System.out.println("==============================");

        // when
        bannerService.updateBanner(1L, updateRequest, newImage);

        // then
        assertEquals("수정된 배너", existingBanner.getTitle());
        assertEquals("수정된 설명", existingBanner.getDescription());
        assertEquals("http://new-link.com", existingBanner.getLink());

        System.out.println("배너 수정 완료: " + existingBanner.getTitle());

        // 수정 후 배너 정보 출력
        System.out.println("수정 후 배너 정보:");
        System.out.println("ID: " + existingBanner.getId());
        System.out.println("제목: " + existingBanner.getTitle());
        System.out.println("설명: " + existingBanner.getDescription());
        System.out.println("링크: " + existingBanner.getLink());
        System.out.println("파일명: " + existingBanner.getBannerOriginalName());
        System.out.println("UUID: " + existingBanner.getBannerUuid());
        System.out.println("==============================");
    }
}
