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
import java.util.*;

import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_01_NOT_FOUND;
import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_08_USER_CREATION;
import static com.fiap.hackathon.common.logging.LoggingPattern.*;

public class DoctorRepositoryImpl implements DoctorRepository {

    private static final String TABLE_NAME = "Doctor";
    private static final String CPF_INDEX = "CpfIndex";
    private static final String EMAIL_INDEX = "EmailIndex";
    private static final String SPECIALTY_INDEX = "SpecialtyIndex";
    private static final String ATTRIBUTES = "id,fullName,birthday,cpf,email,contactNumber,isActive,crm,medicalSpecialty";

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
            final var id = itemValues.get("id").s();

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
                    .keyConditionExpression("cpf = :val")
                    .projectionExpression(ATTRIBUTES)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);
            final var doctors = result.items().stream().map(item -> convertItemToEntity(item)).toList();

            if(doctors.isEmpty()) return null;

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
                    .keyConditionExpression("email = :val")
                    .projectionExpression(ATTRIBUTES)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);
            final var doctors = result.items().stream().map(this::convertItemToEntity).toList();

            if(doctors.isEmpty()) return null;

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
                    .keyConditionExpression("medicalSpecialty = :val")
                    .projectionExpression(ATTRIBUTES)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);

            if(result.items().isEmpty()) return List.of();

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

        itemValues.put("id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        itemValues.put("fullName", AttributeValue.builder().s(doctor.getName()).build());
        itemValues.put("birthday", AttributeValue.builder().s(doctor.getBirthday().toString()).build());
        itemValues.put("cpf", AttributeValue.builder().s(doctor.getCpf()).build());
        itemValues.put("email", AttributeValue.builder().s(doctor.getEmail()).build());
        itemValues.put("password", AttributeValue.builder().s(doctor.getPassword()).build());
        itemValues.put("contactNumber", AttributeValue.builder().s(doctor.getContactNumber()).build());
        itemValues.put("crm", AttributeValue.builder().s(doctor.getCrm()).build());
        itemValues.put("medicalSpecialty", AttributeValue.builder().s(doctor.getMedicalSpecialty().name()).build());
        itemValues.put("isActive", AttributeValue.builder().bool(doctor.getIsActive()).build());

        return itemValues;
    }

    private Doctor convertItemToEntity(Map<String, AttributeValue> item) {
        return new Doctor(
                item.get("fullName").s(),
                LocalDate.parse(item.get("birthday").s()),
                item.get("cpf").s(),
                item.get("email").s(),
                item.get("password").s(),
                item.get("contactNumber").s(),
                item.get("id").s(),
                item.get("isActive").bool(),
                item.get("crm").s(),
                MedicalSpecialtyEnum.valueOf(item.get("medicalSpecialty").s())
        );
    }
}
