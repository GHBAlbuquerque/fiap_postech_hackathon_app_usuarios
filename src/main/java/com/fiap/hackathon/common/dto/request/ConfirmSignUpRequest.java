package com.fiap.hackathon.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmSignUpRequest {

    @NotBlank(message = "The e-mail cannot be blank")
    String email;

    @NotBlank(message = "The code must be a not null")
    String code;

}
