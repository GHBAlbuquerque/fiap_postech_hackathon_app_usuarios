package com.fiap.hackathon.common.builders;

import com.fiap.hackathon.common.dto.request.RegisterPacientRequest;
import com.fiap.hackathon.common.dto.response.GetPacientResponse;
import com.fiap.hackathon.core.entity.Pacient;

import java.time.LocalDateTime;

public class PacientBuilder {

    public PacientBuilder() {
    }

    public static Pacient fromRequestToDomain(RegisterPacientRequest request) {
        return (Pacient) new Pacient()
                .setName(request.getName())
                .setBirthday(request.getBirthday())
                .setCpf(request.getCpf())
                .setCreationTimestamp(LocalDateTime.now())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setContactNumber(request.getContactNumber());
    }

    public static GetPacientResponse fromDomainToResponse(Pacient pacient) {
        return new GetPacientResponse()
                .setId(pacient.getId())
                .setName(pacient.getName())
                .setBirthday(pacient.getBirthday())
                .setCpf(pacient.getCpf())
                .setEmail(pacient.getEmail())
                .setCreationTimestamp(pacient.getCreationTimestamp())
                .setUpdateTimestamp(pacient.getUpdateTimestamp())
                .setIsActive(pacient.getIsActive())
                .setContactNumber(pacient.getContactNumber());
    }

    /*public static Pacient fromOrmToDomain(PacientORM orm) {
        return new Pacient(
                orm.getName(),
                orm.getBirthday(),
                orm.getCpf(),
                orm.getEmail(),
                orm.getPassword(),
                orm.getContactNumber(),
                orm.getCreationTimestamp(),
                orm.getUpdateTimestamp(),
                orm.getId(),
                orm.getIsActive()
        );
    }

    public static PacientORM fromDomainToOrm(Pacient pacient) {
        return new PacientORM()
                .setId(pacient.getId())
                .setName(pacient.getName())
                .setBirthday(pacient.getBirthday())
                .setCpf(pacient.getCpf())
                .setEmail(pacient.getEmail())
                .setPassword(pacient.getPassword())
                .setContactNumber(pacient.getContactNumber())
                .setCreationTimestamp(pacient.getCreationTimestamp())
                .setUpdateTimestamp(pacient.getUpdateTimestamp())
                .setIsActive(pacient.getIsActive());
    }*/
}
