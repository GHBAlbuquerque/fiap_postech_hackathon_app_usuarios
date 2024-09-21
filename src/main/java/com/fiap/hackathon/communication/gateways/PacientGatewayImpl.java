package com.fiap.hackathon.communication.gateways;

import com.fiap.hackathon.common.interfaces.gateways.PacientGateway;
import com.fiap.hackathon.core.entity.Pacient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class PacientGatewayImpl implements PacientGateway {

    private static final String TABLE_NAME = "Doctor";
    private static final String CPF_INDEX = "CpfIndex";
    private static final String EMAIL_INDEX = "EmailIndex";
    private static final String SPECIALTY_INDEX = "SpecialtyIndex";
    private static final String KEY_CONDITION_EXPRESSION = "partitionKey = :val";

    private final DynamoDbClient dynamoDbClient;

    public PacientGatewayImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private static final Logger logger = LogManager.getLogger(DoctorGatewayImpl.class);

    @Override
    public Pacient savePacient(Pacient pacient) {
        return null;
    }

    @Override
    public Pacient getPacientById(String id) {
        return null;
    }

    @Override
    public Pacient getPacientByCpf(String cpf) {
        return null;
    }

    @Override
    public Pacient getPacientByEmail(String cpf) {
        return null;
    }
}
