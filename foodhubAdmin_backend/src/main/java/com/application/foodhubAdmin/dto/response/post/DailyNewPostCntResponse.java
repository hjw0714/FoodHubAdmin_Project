package com.application.foodhubAdmin.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyNewPostCntResponse {

    private Object day;
    private Long postCnt;

}
