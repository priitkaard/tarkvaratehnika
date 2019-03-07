package com.qaengine.models.outputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionList {
    private int totalPages;
    private List<QuestionListElement> questions;
}
