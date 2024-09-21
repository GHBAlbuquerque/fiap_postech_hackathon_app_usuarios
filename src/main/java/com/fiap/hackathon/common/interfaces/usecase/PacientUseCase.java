package com.fiap.hackathon.common.interfaces.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.PacientGateway;
import com.fiap.hackathon.core.entity.Pacient;

public interface PacientUseCase {

    Pacient register(Pacient pacient, PacientGateway pacientGateway, AuthenticationGateway authenticationGateway)
            throws AlreadyRegisteredException, IdentityProviderException;

    Pacient getPacientById(Long id, PacientGateway pacientGateway) throws EntityNotFoundException;

    Boolean confirmPacientSignUp(String cpf, String code, AuthenticationGateway authenticationGateway) throws IdentityProviderException;

    Boolean validateInformationInUse(String email, String cpf, PacientGateway pacientGateway);
}
