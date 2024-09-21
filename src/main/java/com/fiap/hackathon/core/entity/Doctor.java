package com.fiap.hackathon.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Doctor extends User {

    private String id;
    private Boolean isActive;
    private String crm;
    private MedicalSpecialtyEnum medicalSpecialty;

    public Doctor(String name,
                  LocalDate birthday,
                  String cpf,
                  String email,
                  String password,
                  String contactNumber,
                  String id,
                  Boolean isActive,
                  String crm,
                  MedicalSpecialtyEnum specialty) {
        super(name, birthday, cpf, email, password, contactNumber);
        this.id = id;
        this.isActive = isActive;
        this.crm = crm;
        this.medicalSpecialty = specialty;
    }

    public Doctor() {
        super();
    }

}
