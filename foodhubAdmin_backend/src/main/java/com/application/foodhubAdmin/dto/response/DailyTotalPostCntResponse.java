package com.application.foodhubAdmin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyTotalPostCntResponse {

    private Object day;
    private Long postCnt;

}
