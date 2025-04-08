package com.application.foodhubAdmin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class MonthlyTotalPostCntResponse {

    private Object month;
    private Long postCnt;

}
