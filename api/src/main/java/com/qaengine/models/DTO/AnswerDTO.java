package com.qaengine.models.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    @NotNull
    @Size(min = 1)
    private String text;
}
