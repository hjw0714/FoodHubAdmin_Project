package com.application.foodhubAdmin.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswdRequest {

    private String userId;
    private String passwd;

}
