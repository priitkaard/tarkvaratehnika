package com.qaengine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vote {
    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "question_id")
    @JsonIgnore()
    private Question question;

    @ManyToOne()
    @JoinColumn(name = "answer_id")
    @JsonIgnore()
    private Answer answer;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @Column(name = "relative_score")
    private int relativeScore;
}
