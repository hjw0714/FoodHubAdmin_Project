package com.application.foodhubAdmin.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyNewUserCntResponse {

    private Object day;
    private Long userCnt;
}
