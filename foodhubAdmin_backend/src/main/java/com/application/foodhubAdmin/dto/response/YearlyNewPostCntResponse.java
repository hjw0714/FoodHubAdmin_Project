package com.application.foodhubAdmin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyNewPostCntResponse {

    private Object year;
    private Long postCnt;

}
