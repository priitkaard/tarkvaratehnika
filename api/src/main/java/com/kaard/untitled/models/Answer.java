package com.kaard.untitled.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Answer {
    @Id
    @GeneratedValue
    private Integer id;
    private String text;
    private List<Comment> comments = new ArrayList<>();
    private Integer score = 0;
    private boolean accepted = false;

    private Answer(String text) {
        this.text = text;
    }
}
