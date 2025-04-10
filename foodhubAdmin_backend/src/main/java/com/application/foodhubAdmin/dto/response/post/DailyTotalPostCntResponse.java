package com.application.foodhubAdmin.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyTotalPostCntResponse {

    private Object day;
    private Long postCnt;

}
