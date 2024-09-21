package com.fiap.hackathon.common.interfaces.datasources;

import com.fiap.hackathon.external.orm.DoctorORM;

import java.util.List;

public interface DoctorRepository {

    DoctorORM saveDoctor(DoctorORM doctor);

    DoctorORM getDoctorById(Long id);

    DoctorORM getDoctorByCpf(String cpf);

    DoctorORM getDoctorByEmail(String cpf);

    List<DoctorORM> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean active);
}
