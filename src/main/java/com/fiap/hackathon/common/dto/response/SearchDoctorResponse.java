package com.fiap.hackathon.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class SearchDoctorResponse {
    private String id;
    private String name;
    private String email;
    private String crm;
    private String medicalSpecialty;
}
