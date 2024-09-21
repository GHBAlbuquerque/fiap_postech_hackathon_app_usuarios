package com.fiap.hackathon.common.builders;

import com.fiap.hackathon.common.dto.request.RegisterDoctorRequest;
import com.fiap.hackathon.common.dto.response.GetDoctorResponse;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import com.fiap.hackathon.external.orm.DoctorORM;

import java.time.LocalDateTime;

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
                .setCreationTimestamp(LocalDateTime.now())
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
                .setCreationTimestamp(doctor.getCreationTimestamp())
                .setUpdateTimestamp(doctor.getUpdateTimestamp())
                .setIsActive(doctor.getIsActive())
                .setContactNumber(doctor.getContactNumber())
                .setCrm(doctor.getCrm())
                .setMedicalSpecialty(doctor.getMedicalSpecialty().name());
    }

    public static Doctor fromOrmToDomain(DoctorORM orm) {
        return new Doctor(
                orm.getName(),
                orm.getBirthday(),
                orm.getCpf(),
                orm.getEmail(),
                orm.getPassword(),
                orm.getContactNumber(),
                orm.getCreationTimestamp(),
                orm.getUpdateTimestamp(),
                orm.getId(),
                orm.getIsActive(),
                orm.getCrm(),
                MedicalSpecialtyEnum.valueOf(
                        orm.getMedicalSpecialty()
                )
        );
    }

    public static DoctorORM fromDomainToOrm(Doctor doctor) {
        return new DoctorORM()
                .setId(doctor.getId())
                .setName(doctor.getName())
                .setBirthday(doctor.getBirthday())
                .setCpf(doctor.getCpf())
                .setEmail(doctor.getEmail())
                .setPassword(doctor.getPassword())
                .setContactNumber(doctor.getContactNumber())
                .setCreationTimestamp(doctor.getCreationTimestamp())
                .setUpdateTimestamp(doctor.getUpdateTimestamp())
                .setIsActive(doctor.getIsActive())
                .setCrm(doctor.getCrm())
                .setMedicalSpecialty(doctor.getMedicalSpecialty().name());
    }
}
