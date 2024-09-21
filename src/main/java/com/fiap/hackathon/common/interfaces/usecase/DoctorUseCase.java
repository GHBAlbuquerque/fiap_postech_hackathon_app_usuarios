package com.fiap.hackathon.common.interfaces.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import com.fiap.hackathon.core.entity.DoctorTimetable;

import java.util.List;

public interface DoctorUseCase {

    Doctor register(Doctor doctor, DoctorGateway doctorGateway, AuthenticationGateway authenticationGateway)
            throws AlreadyRegisteredException, IdentityProviderException, EntitySearchException, CreateEntityException;

    DoctorTimetable registerTimetable(String doctorId, DoctorTimetable timetable, DoctorGateway doctorGateway);

    Doctor getDoctorById(String id, DoctorGateway doctorGateway) throws EntitySearchException;

    Boolean validateInformationInUse(String email, String cpf, DoctorGateway doctorGateway) throws EntitySearchException, AlreadyRegisteredException;

    List<Doctor> searchDoctorsBySpecialty(MedicalSpecialtyEnum medicalSpecialty, Integer page, Integer size, DoctorGateway doctorGateway) throws EntitySearchException;
}
