package com.application.foodhubAdmin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class YearlyTotalPostCntResponse {

    private Object year;
    private Long postCnt;

}
