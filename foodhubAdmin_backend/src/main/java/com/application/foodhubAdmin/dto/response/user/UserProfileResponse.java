package com.application.foodhubAdmin.dto.response.user;

import com.application.foodhubAdmin.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserProfileResponse {

    private String userId;
    private String passwd;
    private String nickname;
    private String profileUUID;
    private String email;
    private String tel;
    private String gender;
    private LocalDate birthday;

    public static UserProfileResponse of (User user) {
        return  UserProfileResponse.builder()
                .userId(user.getId())
                .passwd(user.getPasswd())
                .nickname(user.getNickname())
                .profileUUID(user.getProfileUUID())
                .email(user.getEmail())
                .tel(user.getTel())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .build();
    }

        



}
