package com.application.foodhubAdmin.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyCategoryPostCntResponse {

    private Object month;
    private String subCateNm;
    private Long postCnt;

}
