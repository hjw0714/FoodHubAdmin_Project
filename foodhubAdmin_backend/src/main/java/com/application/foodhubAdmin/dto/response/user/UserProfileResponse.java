package com.application.foodhubAdmin.dto.response.user;

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

        



}
