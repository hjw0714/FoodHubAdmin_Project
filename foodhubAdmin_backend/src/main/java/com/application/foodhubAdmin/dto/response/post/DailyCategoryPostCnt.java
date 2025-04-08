package com.application.foodhubAdmin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyCategoryPostCnt {

    private Object day;
    private Long postCnt;

}
