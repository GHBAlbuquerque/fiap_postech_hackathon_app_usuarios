package com.fiap.hackathon.common.interfaces.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.core.entity.Doctor;

import java.util.List;

public interface DoctorGateway {

    Doctor save(Doctor doctor) throws CreateEntityException;

    Doctor getDoctorById(String id) throws EntitySearchException;

    Doctor getDoctorByCpf(String cpf) throws EntitySearchException;

    Doctor getDoctorByEmail(String email) throws EntitySearchException;

    List<Doctor> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean isActive) throws EntitySearchException;

}
