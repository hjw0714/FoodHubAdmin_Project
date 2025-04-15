package com.application.foodhubAdmin.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANNER_ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "BANNER_ORIGINAL_NAME", nullable = false)
    private String bannerOriginalName;

    @Column(name = "BANNER_UUID", nullable = false)
    private String bannerUuid;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public void updateBanner(String title, String description, String bannerOriginalName, String bannerUuid) {
        if (bannerOriginalName != null) this.bannerOriginalName = bannerOriginalName;
        if (bannerUuid != null) this.bannerUuid = bannerUuid;
        this.title = title;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }


}
