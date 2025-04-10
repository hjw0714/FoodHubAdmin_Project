package com.application.foodhubAdmin.dto.response.user;

import com.application.foodhubAdmin.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserUpdateResponse {

    private String userId;
    private String nickname;
    private String profileOriginal;
    private String profileUuid;
    private String email;
    private LocalDate birthday;

    public static UserUpdateResponse of (User user) {
        return UserUpdateResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileOriginal(user.getProfileOriginal())
                .profileUuid(user.getProfileUuid())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .build();
    }

}
