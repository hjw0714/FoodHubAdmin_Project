package com.application.foodhubAdmin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class YearlyTotalPostCntResponse {

    private Object year;
    private Long postCnt;

}
