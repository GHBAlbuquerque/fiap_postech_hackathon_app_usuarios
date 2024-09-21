package com.fiap.hackathon.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GetPatientResponse {
    private String id;
    private String name;
    private LocalDate birthday;
    private String cpf;
    private String email;
    private String contactNumber;
    private Boolean isActive;
}
