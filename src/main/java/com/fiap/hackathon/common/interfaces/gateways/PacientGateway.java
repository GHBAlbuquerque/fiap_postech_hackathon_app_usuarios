package com.fiap.hackathon.common.interfaces.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.core.entity.Pacient;

public interface PacientGateway {

    Pacient save(Pacient pacient) throws CreateEntityException;

    Pacient getPacientById(String id) throws EntityNotFoundException;

    Pacient getPacientByCpf(String cpf) throws EntityNotFoundException;

    Pacient getPacientByEmail(String email) throws EntityNotFoundException;

}
