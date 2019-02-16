package com.qaengine.models.inputs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AnswerInput {
    private String text;
    private Long questionId;
}
