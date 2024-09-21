//package com.fiap.hackathon.external.repository;
//
//import com.fiap.hackathon.common.interfaces.datasources.DoctorRepository;
//import com.fiap.hackathon.external.orm.DoctorORM;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
//import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
//import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
//
//import java.util.HashMap;
//import java.util.List;
//
//public class DoctorRepositoryImpl implements DoctorRepository {
//
//    private final String TABLE_NAME = "Doctor";
//
//    private final DynamoDbClient dynamoDbClient;
//
//    public DoctorRepositoryImpl(DynamoDbClient dynamoDbClient) {
//        this.dynamoDbClient = dynamoDbClient;
//    }
//    private static final Logger logger = LogManager.getLogger(DoctorRepositoryImpl.class);
//
//    @Override
//    public DoctorORM saveDoctor(DoctorORM doctor) {
//        final var itemValues = new HashMap<String, AttributeValue>();
//
//
//
//        final var putItemRequest = PutItemRequest.builder()
//                .tableName(TABLE_NAME)
//                .item(itemValues)
//                .build();
//
//
//
//                dynamoDbClient.putItem();
//
//
//        return d;
//    }
//
//    @Override
//    public DoctorORM getDoctorById(Long id) {
//        return null;
//    }
//
//    @Override
//    public DoctorORM getDoctorByCpf(String cpf) {
//        return null;
//    }
//
//    @Override
//    public DoctorORM getDoctorByEmail(String cpf) {
//        return null;
//    }
//
//    @Override
//    public List<DoctorORM> getActiveDoctorsBySpecialty(String medicalSpecialty, Boolean active) {
//        return null;
//    }
//}
