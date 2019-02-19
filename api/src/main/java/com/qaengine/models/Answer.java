package com.qaengine.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private Long questionId;
    private String text;
    private Integer score = 0;
    private boolean accepted = false;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "fk_answer_comment")
    private List<Comment> comments = new ArrayList<>();

    public Answer(Long questionId) {
        this.questionId = questionId;
    }
}
