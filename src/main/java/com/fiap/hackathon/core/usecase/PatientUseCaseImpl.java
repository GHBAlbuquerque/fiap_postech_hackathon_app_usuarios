package com.fiap.hackathon.core.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.PatientGateway;
import com.fiap.hackathon.common.interfaces.usecase.PatientUseCase;
import com.fiap.hackathon.core.entity.Patient;

public class PatientUseCaseImpl implements PatientUseCase {
    @Override
    public Patient register(Patient patient, PatientGateway patientGateway, AuthenticationGateway authenticationGateway) throws AlreadyRegisteredException, IdentityProviderException {
        return null;
    }

    @Override
    public Patient getPatientById(Long id, PatientGateway patientGateway) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Boolean validateInformationInUse(String email, String cpf, PatientGateway patientGateway) {
        return null;
    }
}
