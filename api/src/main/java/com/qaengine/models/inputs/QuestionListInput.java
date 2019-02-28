package com.qaengine.models.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionListInput {
    @NotNull
    private int page;
    @NotNull
    private int limit;
    @NotNull
    private String sort;
    private String direction;
    @NotNull
    private String query;
    @NotNull
    private Long categoryId;
}
