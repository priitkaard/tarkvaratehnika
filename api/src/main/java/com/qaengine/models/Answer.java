package com.qaengine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qaengine.models.ApplicationUser;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private Question question;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer score = 0;

    @OneToMany(mappedBy = "answer", fetch = FetchType.EAGER)
    private List<Vote> votes;

    @Column(nullable = false)
    private boolean accepted = false;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @OneToMany(
            mappedBy = "answer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
}
