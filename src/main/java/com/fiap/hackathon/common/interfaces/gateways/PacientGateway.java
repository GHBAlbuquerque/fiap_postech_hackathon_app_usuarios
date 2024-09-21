package com.fiap.hackathon.common.interfaces.gateways;

import com.fiap.hackathon.core.entity.Pacient;

public interface PacientGateway {

    Pacient savePacient(Pacient pacient);

    Pacient getPacientById(Long id);

    Pacient getPacientByCpf(String cpf);

    Pacient getPacientByEmail(String cpf);

}
