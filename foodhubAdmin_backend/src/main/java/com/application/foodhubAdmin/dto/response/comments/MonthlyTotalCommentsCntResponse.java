package com.application.foodhubAdmin.dto.response.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyTotalCommentsCntResponse {

    private Object month;
    private Long commentsCnt;

}
