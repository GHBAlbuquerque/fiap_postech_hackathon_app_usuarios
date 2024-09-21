package com.fiap.hackathon.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Pacient extends User {

    private String id;
    private Boolean isActive;

    public Pacient(String name,
                   LocalDate birthday,
                   String cpf,
                   String email,
                   String password,
                   String contactNumber,
                   LocalDateTime creationTimestamp,
                   LocalDateTime updateTimestamp,
                   String id,
                   Boolean isActive) {
        super(name, birthday, cpf, email, password, contactNumber, creationTimestamp, updateTimestamp);
        this.id = id;
        this.isActive = isActive;
    }

    public Pacient() {
        super();
    }
}
