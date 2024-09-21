package com.fiap.hackathon.external.repository;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.interfaces.repositories.TimetableRepository;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.DoctorTimetable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class TimetableRepositoryImpl implements TimetableRepository {

    private static final String TABLE_NAME = "Timetable";
    private static final String DOCTORID_INDEX = "DoctorIdIndex";
    private static final String ATTRIBUTES = "sunday,monday,tuesday,wednesday,thursday,friday,saturday";

    private final DynamoDbClient dynamoDbClient;

    public TimetableRepositoryImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private static final Logger logger = LogManager.getLogger(TimetableRepositoryImpl.class);

    @Override
    public DoctorTimetable save(DoctorTimetable doctorTimetable) throws CreateEntityException {
        //TODO
        return null;
    }

    @Override
    public DoctorTimetable getTimetableByDoctorId(String doctorId) throws EntitySearchException {
        //TODO
        return null;
    }
}
