package com.application.foodhubAdmin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthlyTotalPostCntResponse {

    private Object month;
    private Long postCnt;

}
