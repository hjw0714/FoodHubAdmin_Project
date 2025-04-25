package com.application.foodhubAdmin.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID" , nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(name = "FILE_UUID", nullable = false)
    private String fileUuid;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;

    @Column(name = "UPLOAD_DATE")
    private LocalDateTime uploadDate;


}
