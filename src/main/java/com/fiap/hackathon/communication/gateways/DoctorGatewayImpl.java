package com.fiap.hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.repositories.DoctorRepository;
import com.fiap.hackathon.core.entity.Doctor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DoctorGatewayImpl implements DoctorGateway {

    private final DoctorRepository repository;

    public DoctorGatewayImpl(DoctorRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LogManager.getLogger(DoctorGatewayImpl.class);

    @Override
    public Doctor save(Doctor doctor) throws CreateEntityException {
        return repository.save(doctor);
    }

    @Override
    public Doctor getDoctorById(String id) throws EntitySearchException {
        return repository.getDoctorById(id);
    }

    @Override
    public Doctor getDoctorByCpf(String cpf) throws EntitySearchException {
        return repository.getDoctorByCpf(cpf);
    }

    @Override
    public Doctor getDoctorByEmail(String email) throws EntitySearchException {
        return repository.getDoctorByEmail(email);
    }

    @Override
    public List<Doctor> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean isActive) throws EntitySearchException {
        return repository.getActiveDoctorsBySpecialty(medicalSpecialty, isActive);
    }

}
