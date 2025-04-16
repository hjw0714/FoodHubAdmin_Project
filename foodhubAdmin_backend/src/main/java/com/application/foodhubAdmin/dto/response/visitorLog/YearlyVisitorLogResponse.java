package com.application.foodhubAdmin.dto.response.visitorLog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyVisitorLogResponse {

    private Object year;
    private Long visitorCnt;

}
