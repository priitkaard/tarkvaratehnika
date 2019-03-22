package com.qaengine.models.outputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private long questions;
    private long answers;
    private long comments;
    private long users;
}
