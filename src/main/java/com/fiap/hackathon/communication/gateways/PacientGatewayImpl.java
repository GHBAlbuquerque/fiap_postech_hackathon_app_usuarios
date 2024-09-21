package com.fiap.hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntityNotFoundException;
import com.fiap.hackathon.common.interfaces.gateways.PacientGateway;
import com.fiap.hackathon.core.entity.Pacient;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import com.fiap.hackathon.core.entity.Pacient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_01_NOT_FOUND;
import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_08_USER_CREATION;
import static com.fiap.hackathon.common.logging.LoggingPattern.*;
import static com.fiap.hackathon.common.logging.LoggingPattern.GET_ENTITY_ERROR;

public class PacientGatewayImpl implements PacientGateway {

    private static final String TABLE_NAME = "Pacient";
    private static final String CPF_INDEX = "CpfIndex";
    private static final String EMAIL_INDEX = "EmailIndex";
    private static final String KEY_CONDITION_EXPRESSION = "partitionKey = :val";

    private final DynamoDbClient dynamoDbClient;

    public PacientGatewayImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private static final Logger logger = LogManager.getLogger(PacientGatewayImpl.class);

    @Override
    public Pacient save(Pacient pacient) throws CreateEntityException {
        final var itemValues = convertEntityToItem(pacient);

        final var putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(itemValues)
                .build();

        try {
            final var response = dynamoDbClient.putItem(putItemRequest);
            final var id = response.responseMetadata().requestId();

            logger.info(CREATE_ENTITY_SUCCESS, TABLE_NAME, id);

            pacient.setId(id);
            return pacient;

        } catch (DynamoDbException e) {
            logger.error(CREATE_ENTITY_ERROR, e.getMessage());
            throw new CreateEntityException(USER_08_USER_CREATION, e.getMessage());
        }
    }

    @Override
    public Pacient getPacientById(String id) throws EntityNotFoundException {
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
            throw new EntityNotFoundException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Pacient getPacientByCpf(String cpf) throws EntityNotFoundException {
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
            throw new EntityNotFoundException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Pacient getPacientByEmail(String email) throws EntityNotFoundException {
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
            throw new EntityNotFoundException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    private HashMap<String, AttributeValue> convertEntityToItem(Pacient pacient) {
        final var itemValues = new HashMap<String, AttributeValue>();

        itemValues.put("name", AttributeValue.builder().s(pacient.getName()).build());
        itemValues.put("birthday", AttributeValue.builder().s(pacient.getBirthday().toString()).build());
        itemValues.put("cpf", AttributeValue.builder().s(pacient.getCpf()).build());
        itemValues.put("email", AttributeValue.builder().s(pacient.getEmail()).build());
        itemValues.put("password", AttributeValue.builder().s(pacient.getPassword()).build());
        itemValues.put("contactNumber", AttributeValue.builder().s(pacient.getContactNumber()).build());
        itemValues.put("creationTimestamp", AttributeValue.builder().s(pacient.getCreationTimestamp().toString()).build());
        itemValues.put("updateTimestamp", AttributeValue.builder().s(pacient.getUpdateTimestamp().toString()).build());
        itemValues.put("isActive", AttributeValue.builder().bool(pacient.getIsActive()).build());

        return itemValues;
    }

    private Pacient convertItemToEntity(Map<String, AttributeValue> item) {
        return new Pacient(
                item.get("name").s(),
                LocalDate.parse(item.get("birthday").s()),
                item.get("cpf").s(),
                item.get("email").s(),
                item.get("password").s(),
                item.get("contactNumber").s(),
                LocalDateTime.parse(item.get("creationTimestamp").s()),
                LocalDateTime.parse(item.get("updateTimestamp").s()),
                item.get("id").s(),
                item.get("isActive").bool()
        );
    }
}
