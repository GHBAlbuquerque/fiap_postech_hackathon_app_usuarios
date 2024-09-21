package com.fiap.hackathon.common.interfaces.repositories;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.core.entity.Patient;

public interface PatientRepository {

    Patient save(Patient patient) throws CreateEntityException;

    Patient getPatientById(String id) throws EntitySearchException;

    Patient getPatientByCpf(String cpf) throws EntitySearchException;

    Patient getPatientByEmail(String email) throws EntitySearchException;

}
