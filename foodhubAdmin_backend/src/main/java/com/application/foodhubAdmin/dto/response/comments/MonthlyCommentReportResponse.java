package com.application.foodhubAdmin.dto.response.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyCommentReportResponse {

    private Object month;
    private Long reportCnt;

}
