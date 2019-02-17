package com.qaengine.models;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private Long questionId;
    private Long answerid;
    private String text;
    private Integer score = 0;
}
