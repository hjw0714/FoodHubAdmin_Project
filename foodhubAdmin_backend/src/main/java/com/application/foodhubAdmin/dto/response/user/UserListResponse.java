package com.application.foodhubAdmin.dto.response.user;

import com.application.foodhubAdmin.domain.MembershipType;
import com.application.foodhubAdmin.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class UserListResponse {

    private String id;
    private String nickname;
    private String email;
    private LocalDateTime joinAt;
    private MembershipType membershipType;

    public static UserListResponse of(User user) {
        return UserListResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .joinAt(user.getJoinAt())
                .membershipType(user.getMembershipType())
                .build();
    }

}
