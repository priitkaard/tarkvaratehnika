package com.qaengine.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionListDTOOut {
    private int totalPages;
    private List<QuestionListElementDTO> questions;
}
