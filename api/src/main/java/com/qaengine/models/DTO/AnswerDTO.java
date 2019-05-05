package com.qaengine.models.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    @NotNull
    private String text;
}
