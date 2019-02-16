package com.qaengine.models;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private Integer score = 0;

    public Comment(String text) {
        this.text = text;
    }
}
