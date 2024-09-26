package hackathon.external.repository;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.core.entity.Patient;
import com.fiap.hackathon.external.repository.PatientRepositoryImpl;
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
class PatientRepositoryImplTest {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @InjectMocks
    private PatientRepositoryImpl patientRepository;

    @Test
    void shouldSavePatientSuccessfully() throws CreateEntityException {
        // Arrange
        final var patient = createPatient();

        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenReturn(PutItemResponse.builder().build());

        // Act
        final var result = patientRepository.save(patient);

        // Assert
        assertEquals(patient.getName(), result.getName());
    }

    @Test
    void shouldThrowCreateEntityExceptionWhenSaveFails() {
        // Arrange
        final var patient = createPatient();

        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenThrow(DynamoDbException.class);

        // Act & Assert
        assertThrows(CreateEntityException.class, () -> patientRepository.save(patient));
    }

    @Test
    void shouldGetPatientByIdSuccessfully() throws EntitySearchException {
        // Arrange
        final var patientId = UUID.randomUUID().toString();
        final var patientItem = createPatientItem(createPatient());
        patientItem.put("id", AttributeValue.builder().s(patientId).build());

        when(dynamoDbClient.getItem(any(GetItemRequest.class))).thenReturn(GetItemResponse.builder().item(patientItem).build());

        // Act
        final var result = patientRepository.getPatientById(patientId);

        // Assert
        assertEquals(patientId, result.getId());
    }

    @Test
    void shouldThrowEntitySearchExceptionWhenPatientByIdNotFound() {
        // Arrange
        final var patientId = UUID.randomUUID().toString();

        when(dynamoDbClient.getItem(any(GetItemRequest.class))).thenReturn(GetItemResponse.builder().item(Map.of()).build()); // Item vazio

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> patientRepository.getPatientById(patientId));
    }

    @Test
    void shouldGetPatientByCpfSuccessfully() throws EntitySearchException {
        // Arrange
        final var cpf = "12345678900";
        final var patientItem = createPatientItem(createPatient());

        when(dynamoDbClient.query(any(QueryRequest.class))).thenReturn(QueryResponse.builder().items(List.of(patientItem)).build());


        // Act
        final var result = patientRepository.getPatientByCpf(cpf);

        // Assert
        assertEquals(cpf, result.getCpf());
    }

    @Test
    void shouldReturnNullWhenPatientByCpfNotFound() throws EntitySearchException {
        // Arrange
        final var cpf = "12345678900";

        when(dynamoDbClient.query(any(QueryRequest.class))).thenReturn(QueryResponse.builder().items(List.of()).build()); // Lista vazia

        // Act
        final var result = patientRepository.getPatientByCpf(cpf);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldThrowEntitySearchExceptionWhenGetPatientByCpfFails() {
        // Arrange
        final var cpf = "12345678900";
        when(dynamoDbClient.query(any(QueryRequest.class))).thenThrow(RuntimeException.class); // Simulando falha

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> patientRepository.getPatientByCpf(cpf));
    }

    @Test
    void shouldGetPatientByEmailSuccessfully() throws EntitySearchException {
        // Arrange
        final var email = "john.doe@email.com";
        final var patientItem = createPatientItem(createPatient());

        when(dynamoDbClient.query(any(QueryRequest.class))).thenReturn(QueryResponse.builder().items(List.of(patientItem)).build());

        // Act
        final var result = patientRepository.getPatientByEmail(email);

        // Assert
        assertEquals(email, result.getEmail());
    }

    @Test
    void shouldReturnNullWhenPatientByEmailNotFound() throws EntitySearchException {
        // Arrange
        final var email = "john.doe@email.com";

        when(dynamoDbClient.query(any(QueryRequest.class))).thenReturn(QueryResponse.builder().items(List.of()).build());

        // Act
        final var result = patientRepository.getPatientByEmail(email);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldThrowEntitySearchExceptionWhenGetPatientByEmailFails() {
        // Arrange
        final var email = "john.doe@email.com";
        when(dynamoDbClient.query(any(QueryRequest.class))).thenThrow(RuntimeException.class); // Simulando falha

        // Act & Assert
        assertThrows(EntitySearchException.class, () -> patientRepository.getPatientByEmail(email));
    }


    private Patient createPatient() {
        return new Patient(
                "John Doe",
                LocalDate.of(1990, 1, 1),
                "12345678900",
                "john.doe@email.com",
                "securePassword",
                "123456789",
                null,
                true
        );
    }

    private Map<String, AttributeValue> createPatientItem(Patient patient) {
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
}
