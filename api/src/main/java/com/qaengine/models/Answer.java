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

    private String text;
    private Integer score = 0;
    private boolean accepted = false;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();


    private Answer(String text) {
        this.text = text;
    }
}
