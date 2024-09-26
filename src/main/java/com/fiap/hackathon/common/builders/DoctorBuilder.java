package com.fiap.hackathon.common.builders;

import com.fiap.hackathon.common.dto.request.RegisterDoctorRequest;
import com.fiap.hackathon.common.dto.response.GetDoctorResponse;
import com.fiap.hackathon.common.dto.response.SearchDoctorResponse;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;

public class DoctorBuilder {

    public DoctorBuilder() {
    }

    public static Doctor fromRequestToDomain(RegisterDoctorRequest request) {
        final var doctor = (Doctor) new Doctor()
                .setCrm(request.getCrm())
                .setMedicalSpecialty(
                        MedicalSpecialtyEnum.valueOf(
                                request.getMedicalSpecialty()
                        )
                );

        doctor.setName(request.getName())
                .setBirthday(request.getBirthday())
                .setCpf(request.getCpf())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setContactNumber(request.getContactNumber());

        return doctor;
    }

    public static GetDoctorResponse fromDomainToResponse(Doctor doctor) {
        return new GetDoctorResponse()
                .setId(doctor.getId())
                .setName(doctor.getName())
                .setBirthday(doctor.getBirthday())
                .setCpf(doctor.getCpf())
                .setEmail(doctor.getEmail())
                .setIsActive(doctor.getIsActive())
                .setContactNumber(doctor.getContactNumber())
                .setCrm(doctor.getCrm())
                .setMedicalSpecialty(doctor.getMedicalSpecialty().name());
    }

    public static SearchDoctorResponse fromDomainToSearchResponse(Doctor doctor) {
        return new SearchDoctorResponse()
                .setId(doctor.getId())
                .setName(doctor.getName())
                .setEmail(doctor.getEmail())
                .setCrm(doctor.getCrm())
                .setMedicalSpecialty(doctor.getMedicalSpecialty().name());
    }
}
