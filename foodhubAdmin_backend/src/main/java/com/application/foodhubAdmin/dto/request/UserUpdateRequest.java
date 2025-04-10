package com.application.foodhubAdmin.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

	private String nickname;
	private String profileOriginal;
	private String profileUuid;
	private String email;
	private String tel;

}
