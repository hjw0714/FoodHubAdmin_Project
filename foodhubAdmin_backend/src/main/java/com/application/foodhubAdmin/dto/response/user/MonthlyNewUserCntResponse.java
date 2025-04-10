package com.application.foodhubAdmin.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyNewUserCntResponse {
    private Object month;
    private Long userCnt;
}
