package com.application.foodhubAdmin.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "USER_ID", length = 255)
    private String id;

    @Column(name = "PASSWD", nullable = false, length = 255)
    private String passwd;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBERSHIP_TYPE", nullable = false, length = 10)
    private MembershipType membershipType;

    @Column(name = "PROFILE_UUID", length = 255)
    private String profileUUID;

    @Column(name = "PROFILE_ORIGINAL", length = 255)
    private String profileOriginal;

    @Column(name = "NICKNAME", nullable = false, unique = true, length = 255)
    private String nickname;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "TEL", length = 20)
    private String tel;

    @Column(name = "GENDER", nullable = false, length = 1)
    private String gender;

    @Column(name = "SMS_YN", nullable = false, length = 1)
    private String smsYn;

    @Column(name = "EMAIL_YN", nullable = false, length = 1)
    private String emailYn;

    @Column(name = "BUSINESS_TYPE", length = 255)
    private String businessType;

    @Column(name = "FOUNDING_AT")
    private LocalDate foundingAt;

    @Column(name = "JOIN_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinAt;

    @Column(name = "MODIFY_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyAt;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;
}
