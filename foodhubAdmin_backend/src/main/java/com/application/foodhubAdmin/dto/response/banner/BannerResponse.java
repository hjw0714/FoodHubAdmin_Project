package com.application.foodhubAdmin.dto.response.banner;

import com.application.foodhubAdmin.domain.Banner;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BannerResponse {

    private Long id;
    private String title;
    private String description;
    private String bannerOriginalName;
    private String bannerUuid;
    private String link;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BannerResponse of (Banner banner) {
        return BannerResponse.builder()
                .id(banner.getId())
                .title(banner.getTitle())
                .description(banner.getDescription())
                .bannerOriginalName(banner.getBannerOriginalName())
                .bannerUuid(banner.getBannerUuid())
                .link(banner.getLink())
                .createdAt(banner.getCreatedAt())
                .updatedAt(banner.getUpdatedAt())
                .build();
    }

}
