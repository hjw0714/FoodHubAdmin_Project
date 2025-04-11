package com.application.foodhubAdmin.dto.response.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyNewCommentsCntResponse {

    private Object year;
    private Long commentsCnt;

}
