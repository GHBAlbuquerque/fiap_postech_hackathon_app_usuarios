package com.fiap.hackathon.common.interfaces.repositories;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.core.entity.Patient;

public interface PatientRepository {

    Patient save(Patient patient) throws CreateEntityException;

    Patient getPatientById(String id) throws EntityNotFoundException;

    Patient getPatientByCpf(String cpf) throws EntityNotFoundException;

    Patient getPatientByEmail(String email) throws EntityNotFoundException;

}
