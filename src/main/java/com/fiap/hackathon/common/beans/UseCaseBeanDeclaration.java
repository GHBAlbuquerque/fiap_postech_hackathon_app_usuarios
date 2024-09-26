package com.fiap.hackathon.common.beans;

import com.fiap.hackathon.common.interfaces.usecase.DoctorUseCase;
import com.fiap.hackathon.common.interfaces.usecase.PatientUseCase;
import com.fiap.hackathon.core.usecase.DoctorUseCaseImpl;
import com.fiap.hackathon.core.usecase.PatientUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanDeclaration {

    @Bean
    public DoctorUseCase doctorUseCase() {
        return new DoctorUseCaseImpl();
    }

    @Bean
    public PatientUseCase patientUseCase() {
        return new PatientUseCaseImpl();
    }

}
