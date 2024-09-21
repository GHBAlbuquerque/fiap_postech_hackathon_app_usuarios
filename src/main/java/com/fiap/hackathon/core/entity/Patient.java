package com.fiap.hackathon.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
public class Patient extends User {

    private String id;
    private Boolean isActive;

    public Patient(String name,
                   LocalDate birthday,
                   String cpf,
                   String email,
                   String password,
                   String contactNumber,
                   String id,
                   Boolean isActive) {
        super(name, birthday, cpf, email, password, contactNumber);
        this.id = id;
        this.isActive = isActive;
    }

    public Patient() {
        super();
    }
}
