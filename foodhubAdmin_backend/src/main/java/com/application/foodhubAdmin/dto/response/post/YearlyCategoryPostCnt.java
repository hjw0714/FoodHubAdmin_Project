package com.application.foodhubAdmin.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyCategoryPostCnt {

    private Object year;
    private Long postCnt;

}
