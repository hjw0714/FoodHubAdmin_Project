package com.application.foodhubAdmin.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyCategoryPostCntResponse {

    private Object day;
    private Long postCnt;
    private Integer categoryId;

}
