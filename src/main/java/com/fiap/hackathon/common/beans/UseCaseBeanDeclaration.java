package com.fiap.hackathon.common.beans;

import com.fiap.hackathon.common.interfaces.usecase.DoctorUseCase;
import com.fiap.hackathon.common.interfaces.usecase.PacientUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanDeclaration {

    //TODO
    @Bean
    public DoctorUseCase doctorUseCase() {
        return null;
    }

    @Bean
    public PacientUseCase pacientUseCase() {
        return null;
    }

}
