package com.fiap.hackathon.external.repository;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.interfaces.repositories.TimetableRepository;
import com.fiap.hackathon.core.entity.DoctorTimetable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_01_NOT_FOUND;
import static com.fiap.hackathon.common.exceptions.custom.ExceptionCodes.USER_08_USER_CREATION;
import static com.fiap.hackathon.common.logging.LoggingPattern.*;

public class TimetableRepositoryImpl implements TimetableRepository {

    private static final String TABLE_NAME = "Timetable";
    private static final String DOCTOR_ID_INDEX = "DoctorIdIndex";
    private static final String ATTRIBUTES = "sunday,monday,tuesday,wednesday,thursday,friday,saturday";

    private final DynamoDbClient dynamoDbClient;

    public TimetableRepositoryImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private static final Logger logger = LogManager.getLogger(TimetableRepositoryImpl.class);

    @Override
    public DoctorTimetable save(DoctorTimetable doctorTimetable) throws CreateEntityException {
        final var itemValues = convertEntityToItem(doctorTimetable);

        final var putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(itemValues)
                .build();

        try {
            final var response = dynamoDbClient.putItem(putItemRequest);
            final var id = itemValues.get("id").s();

            logger.info(CREATE_ENTITY_SUCCESS, TABLE_NAME, id);

            doctorTimetable.setId(id);
            return doctorTimetable;

        } catch (DynamoDbException e) {
            logger.error(CREATE_ENTITY_ERROR, e.getMessage());
            throw new CreateEntityException(USER_08_USER_CREATION, e.getMessage());
        }
    }

    @Override
    public DoctorTimetable getTimetableByDoctorId(String doctorId) throws EntitySearchException {
        final var expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(doctorId).build());

        try {
            final var queryRequest = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .indexName(DOCTOR_ID_INDEX)
                    .keyConditionExpression("doctorId = :val")
                    .projectionExpression(ATTRIBUTES)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

            final var result = dynamoDbClient.query(queryRequest);
            final var timetables = result.items().stream().map(this::convertItemToEntity).toList();

            if (timetables.isEmpty()) return null;

            return timetables.get(0);

        } catch (Exception e) {
            logger.error(GET_ENTITY_ERROR, doctorId, e.getMessage());
            throw new EntitySearchException(USER_01_NOT_FOUND, e.getMessage());
        }
    }

    private HashMap<String, AttributeValue> convertEntityToItem(DoctorTimetable doctorTimetable) {
        final var itemValues = new HashMap<String, AttributeValue>();

        itemValues.put("id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        itemValues.put("doctorId", AttributeValue.builder().s(doctorTimetable.getDoctorId()).build());
        //itemValues.put("sunday", AttributeValue.builder().ss(doctorTimetable.getSunday()).build());
        itemValues.put("monday", AttributeValue.builder().ss(doctorTimetable.getMonday().toString()).build());
        itemValues.put("tuesday", AttributeValue.builder().ss(doctorTimetable.getTuesday()).build());
        itemValues.put("wednesday", AttributeValue.builder().ss(doctorTimetable.getWednesday()).build());
        itemValues.put("thursday", AttributeValue.builder().ss(doctorTimetable.getThursday()).build());
        itemValues.put("friday", AttributeValue.builder().ss(doctorTimetable.getFriday()).build());
        itemValues.put("saturday", AttributeValue.builder().ss(doctorTimetable.getSaturday()).build());

        return itemValues;
    }

    private DoctorTimetable convertItemToEntity(Map<String, AttributeValue> item) {
        return new DoctorTimetable(
                item.get("id").s(),
                item.get("doctorId").s(),
                item.get("sunday").ss(),
                item.get("monday").ss(),
                item.get("tuesday").ss(),
                item.get("wednesday").ss(),
                item.get("thursday").ss(),
                item.get("friday").ss(),
                item.get("saturday").ss()
        );
    }
}
