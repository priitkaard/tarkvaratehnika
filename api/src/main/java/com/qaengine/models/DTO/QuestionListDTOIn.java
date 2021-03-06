package com.qaengine.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionListDTOIn {
    @NotNull
    private int page;
    @NotNull
    private int limit;
    @NotNull
    private String sort;
    @NotNull
    private String direction;
    @NotNull
    private String query;
    private Long categoryId;
}