package com.qaengine.models.DTO;


import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    @NotNull
    private String text;
    @NotNull
    private String title;
    @NotNull
    private Long categoryId;
}
