package com.application.foodhubAdmin.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyNewPostCntResponse {

    private Object month;
    private Long postCnt;


}