package com.application.postAdvance.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

	private String nickname;
	private String profileOriginalName;
	private String profileUuid;
	private String email;

}
