package com.fiap.hackathon.core.usecase;

import com.fiap.hackathon.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.exceptions.custom.IdentityProviderException;
import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.PacientGateway;
import com.fiap.hackathon.common.interfaces.usecase.PacientUseCase;
import com.fiap.hackathon.core.entity.Pacient;

public class PacientUseCaseImpl implements PacientUseCase {
    @Override
    public Pacient register(Pacient pacient, PacientGateway pacientGateway, AuthenticationGateway authenticationGateway) throws AlreadyRegisteredException, IdentityProviderException {
        return null;
    }

    @Override
    public Pacient getPacientById(Long id, PacientGateway pacientGateway) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Boolean validateInformationInUse(String email, String cpf, PacientGateway pacientGateway) {
        return null;
    }
}
