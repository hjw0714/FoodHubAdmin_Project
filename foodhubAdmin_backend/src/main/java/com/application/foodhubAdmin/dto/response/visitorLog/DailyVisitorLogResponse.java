package com.application.foodhubAdmin.dto.response.visitorLog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyVisitorLogResponse {

    private Object day;
    private Long visitorCnt;

}
