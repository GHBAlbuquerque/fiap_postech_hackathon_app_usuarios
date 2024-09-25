package hackathon.external.repository;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.core.entity.DoctorTimetable;
import com.fiap.hackathon.external.repository.TimetableRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimetableRepositoryImplTest {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @InjectMocks
    private TimetableRepositoryImpl timetableRepository;

    private DoctorTimetable doctorTimetable;

    @BeforeEach
    void setUp() {
        doctorTimetable = createDoctorTimetable();
    }

    @Test
    void testSaveSuccess() throws CreateEntityException {
        // Arrange
        final var id = UUID.randomUUID().toString();
        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenAnswer(invocation -> {
            final var request = invocation.getArgument(0, PutItemRequest.class);
            assertNotNull(request.item().get("id")); // Ensure ID is generated
            return null; // Simulate successful save
        });

        // Act
        final var result = timetableRepository.save(doctorTimetable);

        // Assert
        assertNotNull(result.getId());
        assertEquals(doctorTimetable.getDoctorId(), result.getDoctorId());
    }

    @Test
    void testSaveFailure() {
        // Arrange
        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenThrow(DynamoDbException.class);

        // Act & Assert
        final var exception = assertThrows(CreateEntityException.class, () -> {
            timetableRepository.save(doctorTimetable);
        });
        assertEquals(exception.getClass(), CreateEntityException.class);
    }

    @Test
    void testGetTimetableByDoctorIdSuccess() throws EntitySearchException {
        // Arrange
        var expectedTimetable = createDoctorTimetable();
        expectedTimetable.setId(UUID.randomUUID().toString());

        var items = new HashMap<String, AttributeValue>();
        items.put("id", AttributeValue.builder().s(expectedTimetable.getId()).build());
        items.put("doctorId", AttributeValue.builder().s(expectedTimetable.getDoctorId()).build());
        items.put("sunday", AttributeValue.builder().ss(expectedTimetable.getSunday()).build());
        items.put("monday", AttributeValue.builder().ss(expectedTimetable.getMonday()).build());
        items.put("tuesday", AttributeValue.builder().ss(expectedTimetable.getTuesday()).build());
        items.put("wednesday", AttributeValue.builder().ss(expectedTimetable.getWednesday()).build());
        items.put("thursday", AttributeValue.builder().ss(expectedTimetable.getThursday()).build());
        items.put("friday", AttributeValue.builder().ss(expectedTimetable.getFriday()).build());
        items.put("saturday", AttributeValue.builder().ss(expectedTimetable.getSaturday()).build());

        var queryResponse = QueryResponse.builder().items(List.of(items)).build();
        when(dynamoDbClient.query(any(QueryRequest.class))).thenReturn(queryResponse);

        // Act
        var result = timetableRepository.getTimetableByDoctorId("doctor-123");

        // Assert
        assertNotNull(result);
        assertEquals(expectedTimetable.getId(), result.getId());
    }

    @Test
    void testGetTimetableByDoctorIdNotFound() throws EntitySearchException {
        // Arrange
        var queryResponse = QueryResponse.builder().items(List.of()).build();
        when(dynamoDbClient.query(any(QueryRequest.class))).thenReturn(queryResponse);

        // Act
        var result = timetableRepository.getTimetableByDoctorId("doctor-123");

        // Assert
        assertNull(result);
    }

    @Test
    void testGetTimetableByDoctorIdFailure() {
        // Arrange
        when(dynamoDbClient.query(any(QueryRequest.class))).thenThrow(new RuntimeException("Error"));

        // Act & Assert
        final var exception = assertThrows(EntitySearchException.class, () -> {
            timetableRepository.getTimetableByDoctorId("doctor-123");
        });
        assertEquals("Error", exception.getMessage());
    }

    private DoctorTimetable createDoctorTimetable() {
        return new DoctorTimetable(
                null,
                "doctor-123",
                new HashSet<>(List.of("9:00-10:00")),
                new HashSet<>(List.of("10:00-11:00")),
                new HashSet<>(List.of("11:00-12:00")),
                new HashSet<>(List.of("12:00-13:00")),
                new HashSet<>(List.of("13:00-14:00")),
                new HashSet<>(List.of("14:00-15:00")),
                new HashSet<>(List.of("15:00-16:00"))
        );
    }
}
