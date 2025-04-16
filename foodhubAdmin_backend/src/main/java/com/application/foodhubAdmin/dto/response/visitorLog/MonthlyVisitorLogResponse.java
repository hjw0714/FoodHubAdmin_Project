package com.application.foodhubAdmin.dto.response.visitorLog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyVisitorLogResponse {

    private Object month;
    private Long visitorCnt;

}
