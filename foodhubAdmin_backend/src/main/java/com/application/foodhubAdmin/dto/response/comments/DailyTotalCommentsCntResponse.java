package com.application.foodhubAdmin.dto.response.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyNewCommentsCntResponse {

    private Object day;
    private Long commentsCnt;

}
