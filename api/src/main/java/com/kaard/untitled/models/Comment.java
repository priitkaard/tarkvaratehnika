package com.kaard.untitled.models;


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
    private Integer id;
    private String text;

    public Comment(String text) {
        this.text = text;
    }
}
