package com.fiap.hackathon.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RegisterUserResponse {
    private String id;
}
