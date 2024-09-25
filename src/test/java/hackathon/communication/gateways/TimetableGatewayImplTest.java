package hackathon.communication.gateways;

import com.fiap.hackathon.common.exceptions.custom.CreateEntityException;
import com.fiap.hackathon.common.exceptions.custom.EntitySearchException;
import com.fiap.hackathon.common.interfaces.repositories.TimetableRepository;
import com.fiap.hackathon.communication.gateways.TimetableGatewayImpl;
import com.fiap.hackathon.core.entity.DoctorTimetable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TimetableGatewayImplTest {

    @Mock
    private TimetableRepository repository;

    @InjectMocks
    private TimetableGatewayImpl timetableGateway;

    @Test
    void shouldSaveDoctorTimetableSuccessfully() throws CreateEntityException {
        // Arrange
        final var timetable = createDoctorTimetable();
        when(repository.save(timetable)).thenReturn(timetable);

        // Act
        final var result = timetableGateway.save(timetable);

        // Assert
        assertEquals(timetable, result);
    }

    @Test
    void shouldGetTimetableByDoctorIdSuccessfully() throws EntitySearchException {
        // Arrange
        final var doctorId = "doctorId";
        final var timetable = createDoctorTimetable();
        when(repository.getTimetableByDoctorId(doctorId)).thenReturn(timetable);

        // Act
        final var result = timetableGateway.getTimetableByDoctorId(doctorId);

        // Assert
        assertEquals(timetable, result);
    }

    // Método privado para criar um objeto DoctorTimetable
    private DoctorTimetable createDoctorTimetable() {
        final var timetable = new DoctorTimetable(
                "id",
                "doctor123",
                Set.of(),
                Set.of("10:00-11:00"),
                Set.of("10:00-11:00"),
                Set.of("--:--"),
                Set.of("11:00-12:00"),
                Set.of("--:--"),
                Set.of("10:00-11:00")
        );

        timetable.setDoctorId("doctor123");

        return timetable;
    }
}
