package com.fiap.hackathon.core.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.exceptions.custom.ExceptionCodes;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.usecase.DoctorUseCase;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DoctorUseCaseImpl implements DoctorUseCase {

    private static final Logger logger = LogManager.getLogger(DoctorUseCaseImpl.class);

    @Override
    public Doctor register(Doctor doctor,
                           DoctorGateway doctorGateway,
                           AuthenticationGateway authenticationGateway)
            throws CreateEntityException {

        logger.info("Starting DOCTOR creation...");

        try {
            validateInformationInUse(doctor.getCpf(), doctor.getEmail(), doctorGateway);

            authenticationGateway.createUserAuthentication(
                    doctor.getCpf(),
                    doctor.getPassword(),
                    doctor.getEmail());

            doctor.setIsActive(Boolean.TRUE);

            logger.info("Starting DOCTOR successful...");

            return doctorGateway.save(doctor);

        } catch (Exception ex) {
            logger.error("DOCTOR creation failed.");

            throw new CreateEntityException(
                    ExceptionCodes.USER_08_USER_CREATION,
                    ex.getMessage()
            );
        }
    }

    @Override
    public Doctor getDoctorById(String id, DoctorGateway doctorGateway) throws EntitySearchException {
        logger.info("Getting doctor by id {}", id);

        return doctorGateway.getDoctorById(id);
    }

    @Override
    public Boolean validateInformationInUse(String email, String cpf, DoctorGateway doctorGateway) throws EntitySearchException, AlreadyRegisteredException {

        final var entityUsingEmail = doctorGateway.getDoctorByEmail(email);
        final var entityUsingCpf = doctorGateway.getDoctorByCpf(cpf);

        if (entityUsingEmail != null || entityUsingCpf != null) {
            throw new AlreadyRegisteredException(
                    ExceptionCodes.USER_02_ALREADY_REGISTERED,
                    String.format("Couldn't complete registration for user. Informations %s and %s may be already in use", email, cpf)
            );
        }

        return true;
    }

    @Override
    public List<Doctor> searchDoctorsBySpecialty(MedicalSpecialtyEnum specialty, Integer page, Integer size, DoctorGateway doctorGateway)
            throws EntitySearchException {
        logger.info("Searching for doctors with medical specialty {}", specialty);

        return doctorGateway.getActiveDoctorsBySpecialty(specialty.name(), Boolean.TRUE);
    }
}
