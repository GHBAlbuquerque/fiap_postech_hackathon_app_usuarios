package com.fiap.hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.interfaces.gateways.PatientGateway;
import com.fiap.hackathon.common.interfaces.repositories.PatientRepository;
import com.fiap.hackathon.core.entity.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PatientGatewayImpl implements PatientGateway {

    private final PatientRepository repository;

    public PatientGatewayImpl(PatientRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LogManager.getLogger(PatientGatewayImpl.class);

    @Override
    public Patient save(Patient patient) throws CreateEntityException {
        return repository.save(patient);
    }

    @Override
    public Patient getPatientById(String id) throws EntityNotFoundException {
        return repository.getPatientById(id);
    }

    @Override
    public Patient getPatientByCpf(String cpf) throws EntityNotFoundException {
        return repository.getPatientByCpf(cpf);
    }

    @Override
    public Patient getPatientByEmail(String email) throws EntityNotFoundException {
        return repository.getPatientByEmail(email);
    }
}
