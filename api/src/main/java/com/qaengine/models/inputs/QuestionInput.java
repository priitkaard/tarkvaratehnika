package com.qaengine.models.inputs;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInput {
    @NotNull
    @Size(min = 1)
    private String text;
    @NotNull
    @Size(min = 1)
    private String title;
    @NotNull
    private Long categoryId;
}
