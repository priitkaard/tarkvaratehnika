package com.qaengine.models.outputs;

import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Category;
import com.qaengine.models.Vote;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
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
    private List<Vote> votes;

    public QuestionListElement(
            long id, String title, String text, int score, int views, Date created, Category category,
            ApplicationUser user, long comments, Date lastComment, long answers, Date lastAnswer
    ) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.score = score;
        this.views = views;
        this.created = created;
        this.category = category;
        this.user = user;
        this.comments = comments;
        this.lastComment = lastComment;
        this.answers = answers;
        this.lastAnswer = lastAnswer;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
