package com.qaengine.models.outputs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class QuestionListElement {
    private long id;
    private String title;
    private String text;
    private int score;
    private Date created;
    private long comments;
}
