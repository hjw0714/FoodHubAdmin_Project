package com.application.foodhubAdmin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyCategoryPostCnt {

    private Object month;
    private Long postCnt;

}
