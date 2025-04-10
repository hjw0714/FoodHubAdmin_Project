package com.application.foodhubAdmin.dto.response.user;

import com.application.foodhubAdmin.domain.MembershipType;
import com.application.foodhubAdmin.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserListResponse {

    private String id;
    private String nickname;
    private String email;
    private LocalDateTime joinAt;
    private LocalDateTime deletedAt;
    private MembershipType membershipType;

    public static UserListResponse of(User user) {
        return UserListResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .joinAt(user.getJoinAt())
                .deletedAt(user.getDeletedAt())
                .membershipType(user.getMembershipType())
                .build();
    }

}
