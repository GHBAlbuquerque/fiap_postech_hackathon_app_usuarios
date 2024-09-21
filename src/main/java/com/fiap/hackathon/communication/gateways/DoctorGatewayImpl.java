package com.fiap.hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.repositories.DoctorRepository;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_01_NOT_FOUND;
import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_08_USER_CREATION;
import static com.fiap.hackathon.common.logging.LoggingPattern.*;

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
    public Doctor getDoctorById(String id) throws EntityNotFoundException {
        return repository.getDoctorById(id);
    }

    @Override
    public Doctor getDoctorByCpf(String cpf) throws EntityNotFoundException {
        return repository.getDoctorByCpf(cpf);
    }

    @Override
    public Doctor getDoctorByEmail(String email) throws EntityNotFoundException {
        return repository.getDoctorByEmail(email);
    }

    @Override
    public List<Doctor> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean isActive) throws EntityNotFoundException {
        return repository.getActiveDoctorsBySpecialty(medicalSpecialty, isActive);
    }

}
