package com.fiap.hackathon.common.interfaces.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.core.entity.Doctor;

import java.util.List;

public interface DoctorGateway {

    Doctor saveDoctor(Doctor doctor) throws CreateEntityException;

    Doctor getDoctorById(Long id);

    Doctor getDoctorByCpf(String cpf);

    Doctor getDoctorByEmail(String cpf);

    List<Doctor> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean active);

}
