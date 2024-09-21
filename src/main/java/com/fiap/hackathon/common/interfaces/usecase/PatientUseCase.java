package com.fiap.hackathon.common.interfaces.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.PatientGateway;
import com.fiap.hackathon.core.entity.Patient;

public interface PatientUseCase {

    Patient register(Patient patient, PatientGateway patientGateway, AuthenticationGateway authenticationGateway)
            throws AlreadyRegisteredException, IdentityProviderException;

    Patient getPatientById(Long id, PatientGateway patientGateway) throws EntityNotFoundException;

    Boolean validateInformationInUse(String email, String cpf, PatientGateway patientGateway);
}
