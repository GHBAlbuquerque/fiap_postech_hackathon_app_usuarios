package com.fiap.hackathon.common.beans;

import com.fiap.hackathon.common.interfaces.usecase.DoctorUseCase;
import com.fiap.hackathon.common.interfaces.usecase.PacientUseCase;
import com.fiap.hackathon.core.usecase.DoctorUseCaseImpl;
import com.fiap.hackathon.core.usecase.PacientUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanDeclaration {

    @Bean
    public DoctorUseCase doctorUseCase() {
        return new DoctorUseCaseImpl();
    }

    @Bean
    public PacientUseCase pacientUseCase() {
        return new PacientUseCaseImpl();
    }

}
