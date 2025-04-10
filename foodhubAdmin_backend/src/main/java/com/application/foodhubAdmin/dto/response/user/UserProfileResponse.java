package com.application.foodhubAdmin.dto.response.user;

import com.application.foodhubAdmin.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserProfileResponse {

    private String userId;
    private String nickname;
    private String profileUuid;
    private String email;
    private String tel;
    private String gender;
    private LocalDate birthday;

    public static UserProfileResponse of(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileUuid(user.getProfileUuid())
                .email(user.getEmail())
                .tel(user.getTel())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .build();
    }

}
