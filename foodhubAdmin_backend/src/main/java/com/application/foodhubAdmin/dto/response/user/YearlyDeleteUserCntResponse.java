package com.application.foodhubAdmin.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyDeleteUserCntResponse {
    private Object year;
    private Long userCnt;
}
