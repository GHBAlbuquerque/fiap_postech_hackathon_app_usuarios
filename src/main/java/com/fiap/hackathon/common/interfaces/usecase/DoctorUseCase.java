package com.fiap.hackathon.common.interfaces.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.core.entity.Doctor;

public interface DoctorUseCase {

    Doctor register(Doctor doctor, DoctorGateway doctorGateway, AuthenticationGateway authenticationGateway)
            throws AlreadyRegisteredException, IdentityProviderException;

    Doctor getDoctorById(Long id, DoctorGateway doctorGateway) throws EntityNotFoundException;

    Boolean validateInformationInUse(String email, String cpf, DoctorGateway doctorGateway);
}
