package com.fiap.hackathon.external.repository;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.interfaces.repositories.DoctorRepository;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_01_NOT_FOUND;
import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_08_USER_CREATION;
import static com.fiap.hackathon.common.logging.LoggingPattern.*;

public class DoctorRepositoryImpl implements DoctorRepository {

    private static final String TABLE_NAME = "Doctor";
    private static final String CPF_INDEX = "CpfIndex";
    private static final String EMAIL_INDEX = "EmailIndex";
    private static final String SPECIALTY_INDEX = "SpecialtyIndex";
    private static final String KEY_CONDITION_EXPRESSION = "partitionKey = :val";

    private final DynamoDbClient dynamoDbClient;

    public DoctorRepositoryImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private static final Logger logger = LogManager.getLogger(DoctorRepositoryImpl.class);

    @Override
    public Doctor save(Doctor doctor) throws CreateEntityException {
        final var itemValues = convertEntityToItem(doctor);

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
    public Doctor getDoctorById(String id) throws EntitySearchException {

        final var key = new HashMap<String, AttributeValue>();
        key.put("id", AttributeValue.builder().s(id).build());

        final var getItemRequest = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        try {
            final var result = dynamoDbClient.getItem(getItemRequest);

            logger.info(GET_ENTITY_SUCCESS, TABLE_NAME, id);

            return convertItemToEntity(result.item());

        } catch (Exception e) {
            logger.error(GET_ENTITY_ERROR, id, e.getMessage());
            throw new EntitySearchException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Doctor getDoctorByCpf(String cpf) throws EntitySearchException {
        final var expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(cpf).build());

        try {
            final var queryRequest = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .indexName(CPF_INDEX)
                    .keyConditionExpression(KEY_CONDITION_EXPRESSION)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);
            final var doctors = result.items().stream().map(item -> convertItemToEntity(item)).toList();

            return doctors.get(0);

        } catch (Exception e) {
            logger.error(GET_ENTITY_ERROR, cpf, e.getMessage());
            throw new EntitySearchException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Doctor getDoctorByEmail(String email) throws EntitySearchException {
        final var expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(email).build());

        try {
            final var queryRequest = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .indexName(EMAIL_INDEX)
                    .keyConditionExpression(KEY_CONDITION_EXPRESSION)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);
            final var doctors = result.items().stream().map(this::convertItemToEntity).toList();

            return doctors.get(0);

        } catch (Exception e) {
            logger.error(GET_ENTITY_ERROR, email, e.getMessage());
            throw new EntitySearchException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public List<Doctor> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean active) throws EntitySearchException {
        final var expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(medicalSpecialty).build());

        try {
            final var queryRequest = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .indexName(SPECIALTY_INDEX)
                    .keyConditionExpression(KEY_CONDITION_EXPRESSION)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);

            return result.items().stream()
                    .map(this::convertItemToEntity)
                    .filter(item -> item.getIsActive().equals(active))
                    .toList();

        } catch (Exception e) {
            logger.error(GET_ENTITY_ERROR, medicalSpecialty, e.getMessage());
            throw new EntitySearchException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    private HashMap<String, AttributeValue> convertEntityToItem(Doctor doctor) {
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

    private Doctor convertItemToEntity(Map<String, AttributeValue> item) {
        return new Doctor(
                item.get("name").s(),
                LocalDate.parse(item.get("birthday").s()),
                item.get("cpf").s(),
                item.get("email").s(),
                item.get("password").s(),
                item.get("contactNumber").s(),
                LocalDateTime.parse(item.get("creationTimestamp").s()),
                LocalDateTime.parse(item.get("updateTimestamp").s()),
                item.get("id").s(),
                item.get("isActive").bool(),
                item.get("crm").s(),
                MedicalSpecialtyEnum.valueOf(item.get("medicalSpecity").s())
        );
    }
}
