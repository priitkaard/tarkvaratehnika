package com.qaengine.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String text;
    private Integer score = 0;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Answer> answers = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();
}
