package com.qaengine.models.outputs;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class QuestionListElement {
    private long id;
    private String title;
    private String text;
    private int score;
    private Date created;
    // private boolean canVote;
    private int views = 0;
    private int comments;
}
