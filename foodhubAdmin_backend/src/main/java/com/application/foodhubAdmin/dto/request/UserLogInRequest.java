package com.application.foodhubAdmin.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogInRequest {

    private String userId;
    private String passwd;
}
