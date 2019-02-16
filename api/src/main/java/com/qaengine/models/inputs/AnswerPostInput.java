package com.qaengine.models.inputs;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerPostInput {
    @NotNull
    private String text;
    @NotNull
    private Long questionId;
}
