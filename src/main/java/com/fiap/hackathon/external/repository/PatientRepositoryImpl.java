package com.fiap.hackathon.external.repository;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.interfaces.repositories.PatientRepository;
import com.fiap.hackathon.core.entity.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_01_NOT_FOUND;
import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_08_USER_CREATION;
import static com.fiap.hackathon.common.logging.LoggingPattern.*;

public class PatientRepositoryImpl implements PatientRepository {

    private static final String TABLE_NAME = "Patient";
    private static final String CPF_INDEX = "CpfIndex";
    private static final String EMAIL_INDEX = "EmailIndex";
    private static final String ATTRIBUTES = "id,fullName,birthday,cpf,email,password,contactNumber,isActive";

    private final DynamoDbClient dynamoDbClient;

    public PatientRepositoryImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private static final Logger logger = LogManager.getLogger(PatientRepositoryImpl.class);

    @Override
    public Patient save(Patient patient) throws CreateEntityException {
        final var itemValues = convertEntityToItem(patient);

        final var putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(itemValues)
                .build();

        try {
            final var response = dynamoDbClient.putItem(putItemRequest);
            final var id = itemValues.get("id").s();

            logger.info(CREATE_ENTITY_SUCCESS, TABLE_NAME, id);

            patient.setId(id);
            return patient;

        } catch (DynamoDbException e) {
            logger.error(CREATE_ENTITY_ERROR, e.getMessage());
            throw new CreateEntityException(USER_08_USER_CREATION, e.getMessage());
        }
    }

    @Override
    public Patient getPatientById(String id) throws EntitySearchException {
        final var key = new HashMap<String, AttributeValue>();
        key.put("id", AttributeValue.builder().s(id).build());

        final var getItemRequest = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        try {
            final var result = dynamoDbClient.getItem(getItemRequest);

            if (result.item().isEmpty()) {
                throw new EntitySearchException(USER_01_NOT_FOUND, "No user was found with the requested id.");
            }

            logger.info(GET_ENTITY_SUCCESS, id, TABLE_NAME);

            return convertItemToEntity(result.item());

        } catch (Exception e) {
            logger.error(GET_ENTITY_ERROR, id, e.getMessage());
            throw new EntitySearchException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Patient getPatientByCpf(String cpf) throws EntitySearchException {
        final var expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(cpf).build());

        try {
            final var queryRequest = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .indexName(CPF_INDEX)
                    .keyConditionExpression("cpf = :val")
                    .projectionExpression(ATTRIBUTES)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);
            final var patients = result.items().stream().map(item -> convertItemToEntity(item)).toList();

            if (patients.isEmpty()) return null;

            logger.info(GET_ENTITY_SUCCESS, cpf, TABLE_NAME);

            return patients.get(0);

        } catch (Exception e) {
            logger.error(GET_ENTITY_ERROR, cpf, e.getMessage());
            throw new EntitySearchException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Patient getPatientByEmail(String email) throws EntitySearchException {
        final var expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(email).build());

        try {
            final var queryRequest = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .indexName(EMAIL_INDEX)
                    .keyConditionExpression("email = :val")
                    .projectionExpression(ATTRIBUTES)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);
            final var patients = result.items().stream().map(this::convertItemToEntity).toList();

            if (patients.isEmpty()) return null;

            logger.info(GET_ENTITY_SUCCESS, email, TABLE_NAME);

            return patients.get(0);

        } catch (Exception e) {
            logger.error(GET_ENTITY_ERROR, email, e.getMessage());
            throw new EntitySearchException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    private HashMap<String, AttributeValue> convertEntityToItem(Patient patient) {
        final var itemValues = new HashMap<String, AttributeValue>();

        itemValues.put("id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        itemValues.put("fullName", AttributeValue.builder().s(patient.getName()).build());
        itemValues.put("birthday", AttributeValue.builder().s(patient.getBirthday().toString()).build());
        itemValues.put("cpf", AttributeValue.builder().s(patient.getCpf()).build());
        itemValues.put("email", AttributeValue.builder().s(patient.getEmail()).build());
        itemValues.put("password", AttributeValue.builder().s(patient.getPassword()).build());
        itemValues.put("contactNumber", AttributeValue.builder().s(patient.getContactNumber()).build());
        itemValues.put("isActive", AttributeValue.builder().bool(patient.getIsActive()).build());

        return itemValues;
    }

    private Patient convertItemToEntity(Map<String, AttributeValue> item) {
        return new Patient(
                item.get("fullName").s(),
                LocalDate.parse(item.get("birthday").s()),
                item.get("cpf").s(),
                item.get("email").s(),
                item.get("password").s(),
                item.get("contactNumber").s(),
                item.get("id").s(),
                item.get("isActive").bool()
        );
    }
}
