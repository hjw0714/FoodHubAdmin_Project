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
import java.util.List;

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
    private String profileUuid;

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

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Column(name = "STATUS")
    private String status;

    @OneToMany(mappedBy = "user")
    private List<VisitorLog> visitorLogs;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    // 유저 리스트에서 멤버십 변경
    public void updateMemberShipType(String mem) {
        if(mem.startsWith("사업자")) {
            this.membershipType = MembershipType.BUSSI;
        }
        else if (mem.startsWith("일반")) {
            this.membershipType = MembershipType.COMMON;
        }
        this.modifyAt = LocalDateTime.now();
    }

    // 회원 탈퇴
    public void deleteMember() {
        this.deletedAt = LocalDateTime.now();
        this.status = PostStatus.DELETED.name();
    }

    // 회원 탈퇴 취소
    public void notDeleteMember() {
        this.deletedAt = null;
        this.status = PostStatus.ACTIVE.name();
    }

    // 프로필 업데이트
    public void updateUser (String profileOriginal, String profileUuid, String tel, String email){
        if (profileOriginal != null) this.profileOriginal = profileOriginal;
        if (profileUuid != null) this.profileUuid = profileUuid;
        this.tel = tel;
        this.email = email;
    }

    // 비밀번호 변경
    public void changePasswd (String passwd){
        this.passwd = passwd;
    }

    // Test
    public void User(String id, String passwd, String nickname, String email) {
        this.id = id;
        this.passwd = passwd;
        this.nickname = nickname;
        this.email = email;
        this.membershipType = MembershipType.COMMON;
        this.gender = "M";
        this.smsYn = "Y";
        this.emailYn = "Y";
        this.status = PostStatus.ACTIVE.name();
        this.joinAt = LocalDateTime.now();
        this.modifyAt = LocalDateTime.now();
    }
}
