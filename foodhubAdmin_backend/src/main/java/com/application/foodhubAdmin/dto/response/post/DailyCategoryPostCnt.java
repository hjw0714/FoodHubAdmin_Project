package com.application.foodhubAdmin.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyCategoryPostCnt {

    private Object day;
    private Long postCnt;
    private String subCateNm;

}
