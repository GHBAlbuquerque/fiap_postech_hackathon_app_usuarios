package com.fiap.hackathon.common.interfaces.repositories;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.core.entity.Doctor;

import java.util.List;

public interface DoctorRepository {

    Doctor save(Doctor doctor) throws CreateEntityException;

    Doctor getDoctorById(String id) throws EntityNotFoundException;

    Doctor getDoctorByCpf(String cpf) throws EntityNotFoundException;

    Doctor getDoctorByEmail(String email) throws EntityNotFoundException;

    List<Doctor> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean active) throws EntityNotFoundException;

}
