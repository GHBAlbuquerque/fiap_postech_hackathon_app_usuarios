package com.fiap.hackathon.common.builders;

import com.fiap.hackathon.common.dto.request.RegisterPatientRequest;
import com.fiap.hackathon.common.dto.response.GetPatientResponse;
import com.fiap.hackathon.core.entity.Patient;

import java.time.LocalDateTime;

public class PatientBuilder {

    public PatientBuilder() {
    }

    public static Patient fromRequestToDomain(RegisterPatientRequest request) {
        return (Patient) new Patient()
                .setName(request.getName())
                .setBirthday(request.getBirthday())
                .setCpf(request.getCpf())
                .setCreationTimestamp(LocalDateTime.now())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setContactNumber(request.getContactNumber());
    }

    public static GetPatientResponse fromDomainToResponse(Patient patient) {
        return new GetPatientResponse()
                .setId(patient.getId())
                .setName(patient.getName())
                .setBirthday(patient.getBirthday())
                .setCpf(patient.getCpf())
                .setEmail(patient.getEmail())
                .setCreationTimestamp(patient.getCreationTimestamp())
                .setUpdateTimestamp(patient.getUpdateTimestamp())
                .setIsActive(patient.getIsActive())
                .setContactNumber(patient.getContactNumber());
    }

    /*public static Patient fromOrmToDomain(PatientORM orm) {
        return new Patient(
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

    public static PatientORM fromDomainToOrm(Patient patient) {
        return new PatientORM()
                .setId(patient.getId())
                .setName(patient.getName())
                .setBirthday(patient.getBirthday())
                .setCpf(patient.getCpf())
                .setEmail(patient.getEmail())
                .setPassword(patient.getPassword())
                .setContactNumber(patient.getContactNumber())
                .setCreationTimestamp(patient.getCreationTimestamp())
                .setUpdateTimestamp(patient.getUpdateTimestamp())
                .setIsActive(patient.getIsActive());
    }*/
}
