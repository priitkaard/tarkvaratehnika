package com.qaengine.models.outputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserOutput {
    private long id;
    private String username;
    private int score;
}
