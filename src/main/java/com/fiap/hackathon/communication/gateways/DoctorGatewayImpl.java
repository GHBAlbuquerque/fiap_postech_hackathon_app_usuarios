package com.fiap.hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.interfaces.gateways.DoctorGateway;
import com.fiap.hackathon.core.entity.Doctor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.List;

import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_08_USER_CREATION;
import static com.fiap.hackathon.common.logging.LoggingPattern.CREATE_ENTITY_ERROR;
import static com.fiap.hackathon.common.logging.LoggingPattern.CREATE_ENTITY_SUCCESS;

public class DoctorGatewayImpl implements DoctorGateway {

    private static final String TABLE_NAME = "Doctor";

    private final DynamoDbClient dynamoDbClient;

    public DoctorGatewayImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private static final Logger logger = LogManager.getLogger(DoctorGatewayImpl.class);

    @Override
    public Doctor saveDoctor(Doctor doctor) throws CreateEntityException {
        final var itemValues = convertEntityToItemValues(doctor);

        final var putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(itemValues)
                .build();

        try {
            final var response = dynamoDbClient.putItem(putItemRequest);
            final var id = response.responseMetadata().requestId();

            logger.info(CREATE_ENTITY_SUCCESS, TABLE_NAME, id);

            doctor.setId(id);
            return doctor;

        } catch (DynamoDbException e) {
            logger.error(CREATE_ENTITY_ERROR, e.getMessage());
            throw new CreateEntityException(USER_08_USER_CREATION, e.getMessage());
        }
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return null;
    }

    @Override
    public Doctor getDoctorByCpf(String cpf) {
        return null;
    }

    @Override
    public Doctor getDoctorByEmail(String cpf) {
        return null;
    }

    @Override
    public List<Doctor> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean active) {
        return null;
    }

    private HashMap<String, AttributeValue> convertEntityToItemValues(Doctor doctor) {
        final var itemValues = new HashMap<String, AttributeValue>();

        itemValues.put("name", AttributeValue.builder().s(doctor.getName()).build());
        itemValues.put("birthday", AttributeValue.builder().s(doctor.getBirthday().toString()).build());
        itemValues.put("cpf", AttributeValue.builder().s(doctor.getCpf()).build());
        itemValues.put("email", AttributeValue.builder().s(doctor.getEmail()).build());
        itemValues.put("password", AttributeValue.builder().s(doctor.getPassword()).build());
        itemValues.put("contactNumber", AttributeValue.builder().s(doctor.getContactNumber()).build());
        itemValues.put("creationTimestamp", AttributeValue.builder().s(doctor.getCreationTimestamp().toString()).build());
        itemValues.put("updateTimestamp", AttributeValue.builder().s(doctor.getUpdateTimestamp().toString()).build());
        itemValues.put("crm", AttributeValue.builder().s(doctor.getCrm()).build());
        itemValues.put("medicalSpecity", AttributeValue.builder().s(doctor.getMedicalSpecialty().name()).build());
        itemValues.put("isActive", AttributeValue.builder().bool(doctor.getIsActive()).build());

        return itemValues;
    }
}
