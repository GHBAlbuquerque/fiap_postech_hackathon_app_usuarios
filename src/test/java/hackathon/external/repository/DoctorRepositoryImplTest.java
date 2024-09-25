package hackathon.external.repository;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.core.entity.Doctor;
import com.fiap.hackathon.core.entity.MedicalSpecialtyEnum;
import com.fiap.hackathon.external.repository.DoctorRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorRepositoryImplTest {

    private static final String ATTRIBUTES = "id,fullName,birthday,cpf,email,password,contactNumber,isActive,crm,medicalSpecialty";

    @Mock
    private DynamoDbClient dynamoDbClient;

    @InjectMocks
    private DoctorRepositoryImpl doctorRepository;

    @Test
    void shouldSaveDoctorSuccessfully() throws CreateEntityException {
        // Arrange
        final var doctor = createDoctor();

        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenReturn(PutItemResponse.builder().build());

        // Act
        final var result = doctorRepository.save(doctor);

        // Assert
        assertEquals(doctor.getCpf(), result.getCpf());
    }


    @Test
    void shouldThrowCreateEntityExceptionWhenSaveFails() {
        // Arrange
        final var doctor = createDoctor();

        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenThrow(DynamoDbException.class);

        // Act & Assert
        assertThrows(CreateEntityException.class, () -> doctorRepository.save(doctor));
    }

    @Test
    void shouldGetDoctorByIdSuccessfully() throws EntitySearchException {
        // Arrange
        final var doctorId = "doctorId";
        final var doctorItem = createDoctorItem(createDoctor());
        final var getItemRequest = GetItemRequest.builder().tableName("Doctor").key(Map.of("id", AttributeValue.builder().s(doctorId).build())).build();
        final var getItemResponse = GetItemResponse.builder().item(doctorItem).build();

        when(dynamoDbClient.getItem(getItemRequest)).thenReturn(getItemResponse);

        // Act
        final var result = doctorRepository.getDoctorById(doctorId);

        // Assert
        assertEquals(doctorItem.get("id").s(), result.getId());
    }

    @Test
    void shouldThrowEntitySearchExceptionWhenDoctorNotFound() {
        // Arrange
        final var doctorId = "doctorId";
        final var getItemRequest = GetItemRequest.builder().tableName("Doctor").key(Map.of("id", AttributeValue.builder().s(doctorId).build())).build();
        final var getItemResponse = GetItemResponse.builder().build(); // Item vazio

        when(dynamoDbClient.getItem(getItemRequest)).thenReturn(getItemResponse);

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> doctorRepository.getDoctorById(doctorId));
    }

    @Test
    void shouldGetDoctorByCpfSuccessfully() throws EntitySearchException {
        // Arrange
        final var cpf = "12345678900";
        final var doctorItem = createDoctorItem(createDoctor());
        final var queryRequest = QueryRequest.builder()
                .tableName("Doctor")
                .indexName("CpfIndex")
                .keyConditionExpression("cpf = :val")
                .projectionExpression(ATTRIBUTES)
                .expressionAttributeValues(Map.of(":val", AttributeValue.builder().s(cpf).build()))
                .build();
        final var queryResponse = QueryResponse.builder().items(List.of(doctorItem)).build();

        when(dynamoDbClient.query(queryRequest)).thenReturn(queryResponse);

        // Act
        final var result = doctorRepository.getDoctorByCpf(cpf);

        // Assert
        assertEquals(doctorItem.get("cpf").s(), result.getCpf());
    }

    @Test
    void shouldReturnNullWhenDoctorByCpfNotFound() throws EntitySearchException {
        // Arrange
        final var cpf = "12345678900";
        final var queryRequest = QueryRequest.builder()
                .tableName("Doctor")
                .indexName("CpfIndex")
                .keyConditionExpression("cpf = :val")
                .projectionExpression(ATTRIBUTES)
                .expressionAttributeValues(Map.of(":val", AttributeValue.builder().s(cpf).build()))
                .build();
        final var queryResponse = QueryResponse.builder().items(List.of()).build(); // Lista vazia

        when(dynamoDbClient.query(queryRequest)).thenReturn(queryResponse);

        // Act
        final var result = doctorRepository.getDoctorByCpf(cpf);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldThrowEntitySearchExceptionWhenGetDoctorByCpfFails() {
        // Arrange
        final var cpf = "12345678900";
        final var expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(cpf).build());

        when(dynamoDbClient.query(any(QueryRequest.class))).thenThrow(RuntimeException.class); // Simulando falha

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> doctorRepository.getDoctorByCpf(cpf));
    }

    @Test
    void shouldGetDoctorByEmailSuccessfully() throws EntitySearchException {
        // Arrange
        final var email = "john.doe@email.com";
        final var doctorItem = createDoctorItem(createDoctor());
        final var queryRequest = QueryRequest.builder()
                .tableName("Doctor")
                .indexName("EmailIndex")
                .keyConditionExpression("email = :val")
                .projectionExpression(ATTRIBUTES)
                .expressionAttributeValues(Map.of(":val", AttributeValue.builder().s(email).build()))
                .build();
        final var queryResponse = QueryResponse.builder().items(List.of(doctorItem)).build();

        when(dynamoDbClient.query(queryRequest)).thenReturn(queryResponse);

        // Act
        final var result = doctorRepository.getDoctorByEmail(email);

        // Assert
        assertEquals(doctorItem.get("email").s(), result.getEmail());
    }

    @Test
    void shouldReturnNullWhenDoctorByEmailNotFound() throws EntitySearchException {
        // Arrange
        final var email = "john.doe@email.com";
        final var queryRequest = QueryRequest.builder()
                .tableName("Doctor")
                .indexName("EmailIndex")
                .keyConditionExpression("email = :val")
                .projectionExpression(ATTRIBUTES)
                .expressionAttributeValues(Map.of(":val", AttributeValue.builder().s(email).build()))
                .build();
        final var queryResponse = QueryResponse.builder().items(List.of()).build(); // Lista vazia

        when(dynamoDbClient.query(queryRequest)).thenReturn(queryResponse);

        // Act
        final var result = doctorRepository.getDoctorByEmail(email);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldThrowEntitySearchExceptionWhenGetDoctorByEmailFails() {
        // Arrange
        final var email = "john.doe@email.com";
        final var expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(email).build());

        when(dynamoDbClient.query(any(QueryRequest.class))).thenThrow(RuntimeException.class); // Simulando falha

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> doctorRepository.getDoctorByEmail(email));
    }

    @Test
    void shouldGetActiveDoctorsBySpecialtySuccessfully() throws EntitySearchException {
        // Arrange
        final var specialty = "CARDIOLOGY";
        final var doctorItem = createDoctorItem(createDoctor());
        doctorItem.put("isActive", AttributeValue.builder().bool(true).build());

        final var queryRequest = QueryRequest.builder()
                .tableName("Doctor")
                .indexName("SpecialtyIndex")
                .keyConditionExpression("medicalSpecialty = :val")
                .projectionExpression(ATTRIBUTES)
                .expressionAttributeValues(Map.of(":val", AttributeValue.builder().s(specialty).build()))
                .build();
        final var queryResponse = QueryResponse.builder().items(List.of(doctorItem)).build();

        when(dynamoDbClient.query(queryRequest)).thenReturn(queryResponse);

        // Act
        final var result = doctorRepository.getActiveDoctorsBySpecialty(specialty, true);

        // Assert
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getIsActive());
    }

    @Test
    void shouldReturnEmptyListWhenNoActiveDoctorsBySpecialtyFound() throws EntitySearchException {
        // Arrange
        final var specialty = "CARDIOLOGY";
        final var queryRequest = QueryRequest.builder()
                .tableName("Doctor")
                .indexName("SpecialtyIndex")
                .keyConditionExpression("medicalSpecialty = :val")
                .projectionExpression(ATTRIBUTES)
                .expressionAttributeValues(Map.of(":val", AttributeValue.builder().s(specialty).build()))
                .build();
        final var queryResponse = QueryResponse.builder().items(List.of()).build(); // Lista vazia

        when(dynamoDbClient.query(queryRequest)).thenReturn(queryResponse);

        // Act
        final var result = doctorRepository.getActiveDoctorsBySpecialty(specialty, true);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowEntitySearchExceptionWhenGetActiveDoctorsBySpecialtyFails() {
        // Arrange
        final var specialty = "CARDIOLOGY";
        when(dynamoDbClient.query(any(QueryRequest.class))).thenThrow(RuntimeException.class); // Simulando falha

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> doctorRepository.getActiveDoctorsBySpecialty(specialty, true));
    }


    private Doctor createDoctor() {
        return new Doctor(
                "John Doe",
                LocalDate.of(1985, 5, 15),
                "12345678900",
                "john.doe@email.com",
                "password",
                "999999999",
                null,
                true,
                "12345",
                MedicalSpecialtyEnum.CARDIOLOGISTA
        );
    }

    // Método privado para criar um mapa de atributos
    private Map<String, AttributeValue> createDoctorItem(Doctor doctor) {
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
}

