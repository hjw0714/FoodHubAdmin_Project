package com.application.foodhubAdmin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banner {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANNER_ID" , nullable = false)
    private Long id;

    @Column(name = "TITLE" , nullable = false)
    private String title;

    @Column(name = "DESCRIPTION" , nullable = false)
    private String description;

    @Column(name = "BANNER_ORIGINAL_NAME" , nullable = false)
    private String bannerOriginalName;

    @Column(name = "BANNER_UUID" , nullable = false)
    private String bannerUuid;

    @Column(name = "LINK" , nullable = false)
    private String link;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

}
