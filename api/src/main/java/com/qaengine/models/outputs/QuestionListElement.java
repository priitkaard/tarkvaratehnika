package com.qaengine.models.outputs;

import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Category;
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
    private int views;
    private Date created;
    private Category category;
    private ApplicationUser user;
    private long comments;
    private Date lastComment;
    private long answers;
    private Date lastAnswer;
}
