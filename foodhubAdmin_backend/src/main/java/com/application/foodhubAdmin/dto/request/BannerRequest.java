package com.application.foodhubAdmin.dto.request;

import com.application.foodhubAdmin.domain.Banner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerRequest {

    private Long id;
    private String title;
    private String description;
    private String link;
    private String bannerOriginalName;
    private String bannerUuid;

    public Banner toEntity() {
        return Banner.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .link(this.link)
                .bannerOriginalName(this.bannerOriginalName)
                .bannerUuid(this.bannerUuid)
                .build();
    }

}
