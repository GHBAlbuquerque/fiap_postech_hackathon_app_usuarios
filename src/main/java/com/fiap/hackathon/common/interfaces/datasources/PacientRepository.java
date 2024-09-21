package com.fiap.hackathon.common.interfaces.datasources;

import com.fiap.hackathon.core.entity.Pacient;
import com.fiap.hackathon.external.orm.PacientORM;

public interface PacientRepository {

    PacientORM savePacient(PacientORM pacient);

    PacientORM getPacientById(Long id);

    PacientORM getPacientByCpf(String cpf);

    PacientORM getPacientByEmail(String cpf);
}
