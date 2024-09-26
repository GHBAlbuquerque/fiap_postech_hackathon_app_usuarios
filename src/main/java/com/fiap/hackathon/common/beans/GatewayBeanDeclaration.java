package com.fiap.hackathon.common.beans;

import com.fiap.hackathon.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.common.interfaces.gateways.PatientGateway;
import com.fiap.hackathon.common.interfaces.gateways.TimetableGateway;
import com.fiap.hackathon.common.interfaces.repositories.DoctorRepository;
import com.fiap.hackathon.common.interfaces.repositories.PatientRepository;
import com.fiap.hackathon.common.interfaces.repositories.TimetableRepository;
import com.fiap.hackathon.communication.gateways.AuthenticationGatewayImpl;
import com.fiap.hackathon.communication.gateways.DoctorGatewayImpl;
import com.fiap.hackathon.communication.gateways.PatientGatewayImpl;
import com.fiap.hackathon.communication.gateways.TimetableGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class GatewayBeanDeclaration {

    @Bean
    public DoctorGateway doctorGateway(DoctorRepository doctorRepository) {
        return new DoctorGatewayImpl(doctorRepository);
    }

    @Bean
    public PatientGateway patientGateway(PatientRepository patientRepository) {
        return new PatientGatewayImpl(patientRepository);
    }

    @Bean
    public TimetableGateway timetableGateway(TimetableRepository timetableRepository) {
        return new TimetableGatewayImpl(timetableRepository);
    }

    @Bean
    public AuthenticationGateway authenticationGateway(CognitoIdentityProviderClient cognitoIdentityProviderClient) {
        return new AuthenticationGatewayImpl(cognitoIdentityProviderClient);
    }
}
