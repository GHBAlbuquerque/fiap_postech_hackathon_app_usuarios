package com.fiap.hackathon.core.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.exceptions.custom.ExceptionCodes;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.gateways.TimetableGateway;
import com.fiap.hackathon.common.interfaces.usecase.DoctorUseCase;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.DoctorTimetable;
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
            validateInformationInUse(doctor.getEmail(), doctor.getCpf(), doctorGateway);

            /*authenticationGateway.createUserAuthentication(
                    doctor.getCpf(),
                    doctor.getPassword(),
                    doctor.getEmail());*/ //TODO

            doctor.setIsActive(Boolean.TRUE);

            logger.info("DOCTOR successfully created.");

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
    public DoctorTimetable registerDoctorTimetable(DoctorTimetable timetable, TimetableGateway timetableGateway) throws CreateEntityException {
        final var doctorId = timetable.getDoctorId();
        logger.info("Creating TIMETABLE for DOCTOR with id {}", doctorId);

        try {
            timetable.isValid();

            final var existingTimetable = getTimetableByDoctorId(doctorId, timetableGateway);
            if(existingTimetable != null) {
                throw new CreateEntityException(
                        ExceptionCodes.USER_10_TIMETABLE_CREATION,
                        "Timetable for this doctor id already exists."
                );
            }

            return timetableGateway.save(timetable);

        } catch (Exception ex) {
            logger.error("TIMETABLE creation failed.");

            throw new CreateEntityException(
                    ExceptionCodes.USER_10_TIMETABLE_CREATION,
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

        logger.info("Validating information for entity creation...");

        if (entityUsingEmail != null || entityUsingCpf != null) {
            final var message = String.format("Couldn't complete registration for user. Informations email '%s' and/or cpf '%s' may be already in use.", email, cpf);
            logger.error(message);

            throw new AlreadyRegisteredException(
                    ExceptionCodes.USER_02_ALREADY_REGISTERED,
                    message
            );
        }

        return true;
    }

    @Override
    public List<Doctor> searchDoctorsBySpecialty(MedicalSpecialtyEnum medicalSpecialty, Integer page, Integer size, DoctorGateway doctorGateway)
            throws EntitySearchException {
        logger.info("Searching for doctors with medical specialty {}", medicalSpecialty);

        return doctorGateway.getActiveDoctorsBySpecialty(medicalSpecialty.name(), Boolean.TRUE);
    }

    @Override
    public DoctorTimetable getTimetableByDoctorId(String id, TimetableGateway timetableGateway) throws EntitySearchException {
        logger.info("Searching for doctor {}'s timetable...", id);

        return timetableGateway.getTimetableByDoctorId(id);
    }
}
