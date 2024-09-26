package com.fiap.hackathon.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GetDoctorResponse {
    private String id;
    private String name;
    private LocalDate birthday;
    private String cpf;
    private String email;
    private String contactNumber;
    private Boolean isActive;
    private String crm;
    private String medicalSpecialty;
}
