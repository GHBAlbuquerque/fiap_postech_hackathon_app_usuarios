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
                .setIsActive(patient.getIsActive())
                .setContactNumber(patient.getContactNumber());
    }
}
