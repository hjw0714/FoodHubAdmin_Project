package com.application.foodhubAdmin.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyDeleteUserCntResponse {
    private Object month;
    private Long userCnt;
}
