package com.qaengine.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Question {
    @Id
    @GeneratedValue
    private Integer id;

    private String text;
    private Integer score = 0;
    @OneToMany
    private List<Answer> answers = new ArrayList<>();
    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    public Question(String text) {
        this.text = text;
    }
}
