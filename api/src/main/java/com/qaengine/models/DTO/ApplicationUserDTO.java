package com.qaengine.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserDTO {
    @Size(min = 6, max = 32, message = "Username should be between 6 and 32 characters!")
    private String username;

    @Size(min = 6, max = 32, message = "Password should be between 6 and 32 characters!")
    private String password;
}
