package com.qaengine.models.inputs;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerInput {
    @NotNull
    private String text;
}
