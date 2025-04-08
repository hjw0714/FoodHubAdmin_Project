package com.application.foodhubAdmin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class DailyTotalPostCntResponse {

    private Object day;
    private Long postCnt;

}
