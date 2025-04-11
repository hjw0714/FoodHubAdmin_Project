package com.application.foodhubAdmin.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyCategoryPostCntResponse {

    private Object month;
    private Long postCnt;
    private Integer categoryId;

}
