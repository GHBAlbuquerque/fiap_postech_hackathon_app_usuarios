package com.fiap.hackathon.core.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.usecase.DoctorUseCase;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;

import java.util.List;

public class DoctorUseCaseImpl implements DoctorUseCase {


    @Override
    public Doctor register(Doctor doctor, DoctorGateway doctorGateway, AuthenticationGateway authenticationGateway) throws AlreadyRegisteredException, IdentityProviderException {
        return null;
    }

    @Override
    public Doctor getDoctorById(Long id, DoctorGateway doctorGateway) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Boolean validateInformationInUse(String email, String cpf, DoctorGateway doctorGateway) {
        return null;
    }

    @Override
    public List<Doctor> searchDoctorsBySpecialty(MedicalSpecialtyEnum type, Integer page, Integer size, DoctorGateway doctorGateway) {
        return null;
    }
}
