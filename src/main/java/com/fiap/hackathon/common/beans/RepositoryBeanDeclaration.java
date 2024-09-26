package com.fiap.hackathon.common.beans;

import com.fiap.hackathon.common.interfaces.repositories.DoctorRepository;
import com.fiap.hackathon.common.interfaces.repositories.PatientRepository;
import com.fiap.hackathon.external.repository.DoctorRepositoryImpl;
import com.fiap.hackathon.external.repository.PatientRepositoryImpl;
import com.fiap.hackathon.external.repository.TimetableRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class RepositoryBeanDeclaration {

    @Bean
    public DoctorRepository doctorRepository(DynamoDbClient dynamoDbClient) {
        return new DoctorRepositoryImpl(dynamoDbClient);
    }

    @Bean
    public PatientRepository patientRepository(DynamoDbClient dynamoDbClient) {
        return new PatientRepositoryImpl(dynamoDbClient);
    }

    @Bean
    public TimetableRepositoryImpl timetableRepository(DynamoDbClient dynamoDbClient) {
        return new TimetableRepositoryImpl(dynamoDbClient);
    }
}
